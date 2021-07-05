package com.doubleslash.fifth.service;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.doubleslash.fifth.dto.CommentDTO;
import com.doubleslash.fifth.dto.DetailReviewWriteDTO;
import com.doubleslash.fifth.dto.MyReviewDTO;
import com.doubleslash.fifth.dto.ReviewDTO;
import com.doubleslash.fifth.dto.ReviewWriteDTO;
import com.doubleslash.fifth.dto.response.CommentResponse;
import com.doubleslash.fifth.dto.response.LoveResponse;
import com.doubleslash.fifth.dto.response.ReviewResponse;
import com.doubleslash.fifth.entity.User;
import com.doubleslash.fifth.entity.alcohol.Alcohol;
import com.doubleslash.fifth.entity.review.Comment;
import com.doubleslash.fifth.entity.review.CommentReport;
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
	private final ReviewDetailRepository reviewDetailRepository;
	private final ReviewReportRepository reviewReportRepository;
	private final ReviewLoveRepository reviewLoveRepository;
	private final CommentRepository commentRepository;
	private final CommentReportRepository commentReportRepository;
	private final AlcoholRepository alcoholRepository;

	// 리뷰 조회
	public ReviewResponse<ReviewDTO> getReviews(Long aid, int page, Long id) throws IOException {

		List<ReviewDetail> reviews = reviewDetailRepository.findByAid(aid, id, PageRequest.of(page, 20, Sort.Direction.ASC, "id"));

		List<ReviewDTO> res = reviews.stream()
		.map(r -> new ReviewDTO(r, id))
		.collect(Collectors.toList());
		
		return new ReviewResponse<ReviewDTO>(res, reviewRepository.findCountByAid(aid));
	}
	
	// 리뷰 작성
	@Transactional
	public String writeReview(Long aid, Long id, ReviewWriteDTO reviewWriteDto) {

		User user = searchUser(id);
		Alcohol alcohol = alcoholRepository.findById(aid)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		
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

		User user = searchUser(id);
		Review review = searchReview(rid);
		
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
		
		User user = searchUser(id);
		Review review = searchReview(rid);
		
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

		User user = searchUser(id);
		Review review = searchReview(rid);
		
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

		User user = searchUser(id);
		Review review = searchReview(rid);
		
		Comment comment = new Comment();
		comment.addComment(user, review, content);
		Comment resComment = commentRepository.save(comment);
		
		return new CommentDTO(resComment);
	}
	
	// 댓글 신고
	@Transactional
	public String reportComment(Long id, Long cid, String content) throws IOException {

		User user = searchUser(id);
		Comment comment = commentRepository.findById(cid)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		
		CommentReport commentReport = new CommentReport();
		commentReport.addReportComment(user, comment, content);
		commentReportRepository.save(commentReport);
		
		return "succes";
	} 

	//내 기록 조회
	public ReviewResponse<MyReviewDTO> getMyReviewList(Long id, String sort, String sortOption, int page) {
		if(!sort.equals("abv")) {
			sort = "createdDate";
		} else {
			sort = "alcohol.abv";
		}

		Page<ReviewDetail> myReview = reviewDetailRepository.findByUserIdOrderBy(id, PageRequest.of(page, 20, dirOption(sortOption), sort));
	
		List<MyReviewDTO> res = myReview.stream()
				.map(r -> new MyReviewDTO(r))
				.collect(Collectors.toList());
		
		return new ReviewResponse<MyReviewDTO>(res, myReview.getTotalElements());
	}
	
	//내 기록 삭제
	public void deleteMyReview(List<Long> rid) {
		for(Long i : rid) {
			reviewRepository.deleteById(i);
		}
	}

	//내 기록 수정
	@Transactional
	public void updateMyReview(ReviewWriteDTO reviewWriteDTO, Long id, Long rid) {
		
		ReviewDetail detail = reviewDetailRepository.findById(rid)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		
		if (reviewWriteDTO.getContent() != null) {
			detail.setContent(reviewWriteDTO.getContent());
		}
		
		if (reviewWriteDTO.getStar() != null) {
			detail.setStar(reviewWriteDTO.getStar());
		}
		
		if (reviewWriteDTO.getDetail() != null) {
			detail.setDate(reviewWriteDTO.getDetail().getDate());
			detail.setPlace(reviewWriteDTO.getDetail().getPlace());
			detail.setDrink(reviewWriteDTO.getDetail().getDrink());
			detail.setHangover(reviewWriteDTO.getDetail().getHangover());
			detail.setPrice(reviewWriteDTO.getDetail().getPrice());
			detail.setPrivacy(reviewWriteDTO.getDetail().isPrivacy());
		}
	}
	
    private ReviewLove notAlreadyLove(User user, Review review) {
        Optional<ReviewLove> reviewLove = reviewLoveRepository.findByUserAndReview(user, review);
        if (reviewLove.isPresent()) {
        	return reviewLove.get();
        } else {
        	return null;
        }
    }
	
	//정렬 기준별 오름차순, 내림차순 구분
	private Direction dirOption(String sortOption) {
		if(sortOption.equals("desc")) return Sort.Direction.DESC;
		return Sort.Direction.ASC;
	}
	
	public User searchUser(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	public Review searchReview(Long id) {
		return reviewRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

}
