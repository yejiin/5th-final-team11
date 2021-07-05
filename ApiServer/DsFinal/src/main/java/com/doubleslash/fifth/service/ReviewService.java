package com.doubleslash.fifth.service;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.doubleslash.fifth.dto.CommentDTO;
import com.doubleslash.fifth.dto.ContentDTO;
import com.doubleslash.fifth.dto.DetailReviewWriteDTO;
import com.doubleslash.fifth.dto.LoveResponse;
import com.doubleslash.fifth.dto.ReviewDetailDTO;
import com.doubleslash.fifth.dto.MyReviewDTO;
import com.doubleslash.fifth.dto.ReviewDTO;
import com.doubleslash.fifth.dto.ReviewWriteDTO;
import com.doubleslash.fifth.dto.WrapperDTO;
import com.doubleslash.fifth.dto.comment.CommentResponse;
import com.doubleslash.fifth.dto.review.ReviewResponse;
import com.doubleslash.fifth.entity.Comment;
import com.doubleslash.fifth.entity.CommentReport;
import com.doubleslash.fifth.entity.User;
import com.doubleslash.fifth.entity.alcohol.Alcohol;
import com.doubleslash.fifth.entity.alcohol.AlcoholLove;
import com.doubleslash.fifth.entity.review.Review;
import com.doubleslash.fifth.entity.review.ReviewDetail;
import com.doubleslash.fifth.entity.review.ReviewLove;
import com.doubleslash.fifth.entity.review.ReviewReport;
import com.doubleslash.fifth.repository.AlcoholRepository;
import com.doubleslash.fifth.repository.CommentReportRepository;
import com.doubleslash.fifth.repository.CommentRepository;
import com.doubleslash.fifth.repository.ReviewDetailRepository;
import com.doubleslash.fifth.repository.ReviewLoveRepository;
import com.doubleslash.fifth.repository.ReviewReportRepository;
import com.doubleslash.fifth.repository.ReviewRepository;
import com.doubleslash.fifth.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

	private final UserRepository userRepository;
	private final ReviewRepository reviewRepository;
	private final ReviewDetailRepository detailReviewRepository;
	private final ReviewReportRepository reviewReportRepository;
	private final ReviewLoveRepository reviewLoveRepository;
	private final CommentRepository commentRepository;
	private final CommentReportRepository commentReportRepository;
	private final AlcoholRepository alcoholRepository;
	private final ReviewDetailRepository reviewDetailRepository;

	// 리뷰 조회
	public ReviewResponse getReviews(Long aid, int page, Long id) throws IOException {

		List<ReviewDetail> reviews = reviewDetailRepository.findByAid(aid, id, PageRequest.of(page, 20, Sort.Direction.ASC, "id"));

		List<ReviewDTO> res = reviews.stream()
		.map(r -> new ReviewDTO(r, id))
		.collect(Collectors.toList());
		
		return new ReviewResponse(res, reviewRepository.findCountByAid(aid));
	}
	
	// 리뷰 작성
	@Transactional
	public String writeReview(Long aid, Long id, ReviewWriteDTO reviewWriteDto) {

		User user = userRepository.findById(id).get();
		Alcohol alcohol = alcoholRepository.findById(aid).get();
		
		// 하루 리뷰 제한 체크
		Optional<Review> chk = reviewRepository.findByUserAndAlcoholAndCreatedDate(user, alcohol, LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0)), LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 49)));

		if(chk.isPresent()) {
			return null;
		}
		
        ReviewDetail review = new ReviewDetail();
        
        review.addReview(user, alcohol, reviewWriteDto.getStar(), reviewWriteDto.getContent());
        
        DetailReviewWriteDTO detail = reviewWriteDto.getDetail();
        if (detail != null) {
            review.setDate(detail.getDate());
            review.setPlace(detail.getPlace());
            review.setDrink(detail.getDrink());
            review.setHangover(detail.getHangover());
            review.setPrice(detail.getPrice());
            review.setPrivacy(detail.isPrivacy());
        }
        reviewRepository.save(review);
        

		return "success";
	}
	
	// 리뷰 좋아요
	@Transactional
	public LoveResponse addLoveReview(Long id, Long rid) throws SQLIntegrityConstraintViolationException, IOException{

		User user = userRepository.findById(id).get();
		Review review = reviewRepository.findById(rid).get();
		
		if(notAlreadyLove(user, review) == null) {
			ReviewLove reviewLove = new ReviewLove();
			reviewLove.addLoveReview(user, review);
			reviewLoveRepository.save(reviewLove);
		}
		return new LoveResponse(true, review.getReviewLoves().size());
	}
	
	// 리뷰 좋아요 취소
	@Transactional
	public LoveResponse cancleLoveReview(Long id, Long rid) throws IOException {
		
		User user = userRepository.findById(id).get();
		Review review = reviewRepository.findById(rid).get();
		
		ReviewLove reviewLove = notAlreadyLove(user, review);

        if (reviewLove != null){
        	review.getReviewLoves().remove(reviewLove);
            reviewLoveRepository.delete(reviewLove);
        }
        return new LoveResponse(false, review.getReviewLoves().size());
	}
	
	// 리뷰 신고
	@Transactional
	public String reportReview(Long id, Long rid, String content) throws IOException {

		User user = userRepository.findById(id).get();
		Review review = reviewRepository.findById(rid).get();
		
		ReviewReport reviewReport = new ReviewReport();
		reviewReport.addReport(user, review, content);
		reviewReportRepository.save(reviewReport);

		return "success";
	}
	
	// 댓글 조회
	public CommentResponse getComments(Long rid, int page) {
		
		Page<Comment> comment = commentRepository.findByRid(rid, PageRequest.of(page, 20,  Sort.Direction.ASC, "id"));
		
		List<CommentDTO> commentDto = comment.stream()
				.map(c -> new CommentDTO(c))
				.collect(Collectors.toList());
		
		return new CommentResponse(commentDto, comment.getTotalElements());
	}
	
	// 댓글 작성
	@Transactional
	public CommentDTO writeComment(Long id, Long rid, String content) throws IOException {

		User user = userRepository.findById(id).get();
		Review review = reviewRepository.findById(rid).get();
		
		Comment comment = new Comment();
		comment.addComment(user, review, content);
		Comment resComment = commentRepository.save(comment);
		
		return new CommentDTO(resComment);
	}
	
	// 댓글 신고
	@Transactional
	public String reportComment(Long id, Long cid, String content) throws IOException {

		User user = userRepository.findById(id).get();
		Comment comment = commentRepository.findById(cid).get();
		
		CommentReport commentReport = new CommentReport();
		commentReport.addReportComment(user, comment, content);
		commentReportRepository.save(commentReport);
		
		return "succes";
	}


	
    private ReviewLove notAlreadyLove(User user, Review review) {
        Optional<ReviewLove> reviewLove = reviewLoveRepository.findByUserAndReview(user, review);
        if (reviewLove.isPresent()) {
        	return reviewLove.get();
        } else {
        	return null;
        }
    } 

	// 리뷰 id 확인
	public int reviewChk(Long rid) {
		int result = 0;

		if(reviewRepository.findById(rid).isPresent() == true) {
			result = 1;
		}

		return result;
	}

	//내 기록 조회
	public Map<String, Object> getMyReviewList(Long id, String sort, String sortOption, int page) {
		if(!sort.equals("abv")) sort = "create_time";
		Page<MyReviewDTO> myReviewTemp;
		if(sort.equals("create_time")) {
			myReviewTemp = reviewRepository.getMyReviewListOrderByLatest(id, PageRequest.of(page, 20, dirOption(sortOption), sort));
		}else {
			myReviewTemp = reviewRepository.getMyReviewListOrderByAbv(id, PageRequest.of(page, 20));
		}
		
		List<MyReviewDTO> dto = new ArrayList<MyReviewDTO>();
		
		for(MyReviewDTO m : myReviewTemp) {
			ReviewDetailDTO detailReview = detailReviewRepository.findByRid(m.getRid());
			dto.add(new MyReviewDTO(m.getRid(), m.getAid(), m.getName(), m.getStar(), m.getThumbnail(), m.getContent(), detailReview));
		}
		
		Map<String, Object> res = new TreeMap<>();
		res.put("reviewList", dto);
		res.put("totalCnt", myReviewTemp.getTotalElements());
		
		return res;
	}

	
	//내 기록 수정
	@Transactional
	public WrapperDTO updateMyReview(ReviewWriteDTO requestBody, Long id, Long rid) {
		ReviewDetail detailVo = new ReviewDetail();
	
		Review review = reviewRepository.findById(rid).get();
		review.setStar(requestBody.getStar());
		review.setContent(requestBody.getContent());
		
		if (requestBody.getDetail() == null) {
			return new WrapperDTO("success");
		} else { // 상세 리뷰 있으면 상세 리뷰 저장
			detailVo.setId(rid);
			detailVo.setDate(requestBody.getDetail().getDate());
			detailVo.setPlace(requestBody.getDetail().getPlace());
			detailVo.setDrink(requestBody.getDetail().getDrink());
			detailVo.setHangover(requestBody.getDetail().getHangover());
			detailVo.setPrice(requestBody.getDetail().getPrice());
			detailVo.setPrivacy(requestBody.getDetail().isPrivacy());

			detailReviewRepository.save(detailVo);
		}

		return new WrapperDTO("success");
		
	}
	
	
	//내 기록 삭제
	public void deleteMyReview(List<Long> rid) {
		for(Long i : rid) {
			reviewRepository.deleteById(i);
		}
	}
	
	//정렬 기준을 동적으로 설정
	private Sort sortOption(Direction direction, String property) {
		List<Order> orders = new ArrayList<Sort.Order>();
		orders.add(new Order(direction, property));
		return Sort.by(orders);
	}
	
	//정렬 기준별 오름차순, 내림차순 구분
	private Direction dirOption(String sortOption) {
		if(sortOption.equals("desc")) return Sort.Direction.DESC;
		return Sort.Direction.ASC;
	}

}
