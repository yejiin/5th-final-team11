package com.doubleslash.fifth.service;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.doubleslash.fifth.dto.CommentDTO;
import com.doubleslash.fifth.dto.ContentDTO;
import com.doubleslash.fifth.dto.DetailReviewDTO;
import com.doubleslash.fifth.dto.ReviewDTO;
import com.doubleslash.fifth.dto.ReviewWriteDTO;
import com.doubleslash.fifth.dto.WrapperDTO;
import com.doubleslash.fifth.repository.AlcoholRepository;
import com.doubleslash.fifth.repository.CommentRepository;
import com.doubleslash.fifth.repository.DetailReviewRepository;
import com.doubleslash.fifth.repository.ReportReviewRepository;
import com.doubleslash.fifth.repository.ReportCommentRepository;
import com.doubleslash.fifth.repository.ReviewLoveRepository;
import com.doubleslash.fifth.repository.ReviewRepository;
import com.doubleslash.fifth.vo.AlcoholVO;
import com.doubleslash.fifth.vo.CommentVO;
import com.doubleslash.fifth.vo.DetailReviewVO;
import com.doubleslash.fifth.vo.ReportCommentVO;
import com.doubleslash.fifth.vo.ReportReviewVO;
import com.doubleslash.fifth.vo.ReviewLoveVO;
import com.doubleslash.fifth.vo.ReviewVO;

@Service
public class ReviewService {

	@Autowired
	ReviewRepository reviewRepository;

	@Autowired
	DetailReviewRepository detailReviewRepository;

	@Autowired
	ReviewLoveRepository reviewLoveRepository;

	@Autowired
	CommentRepository commentRepository;

	@Autowired
	ReportReviewRepository reportReviewRepository;

	@Autowired
	ReportCommentRepository reportCommentRepository;

	@Autowired
	AlcoholRepository alcoholRepository;

	// 리뷰 조회
	public Map<String, Object> getReviewList(int aid, int reviewPage, int id, HttpServletResponse response)
			throws IOException {

		Page<ReviewDTO> reviewDto = reviewRepository.findByAid(aid,
				PageRequest.of(reviewPage, 20, Sort.Direction.ASC, "rid"));

		for (int i = 0; i < reviewDto.getContent().size(); i++) {
			int rid = reviewDto.getContent().get(i).getRid();

			// 해당 리뷰 하트 클릭 여부 확인
			ReviewLoveVO loveClick = reviewLoveRepository.findByIdRid(id, rid);
			if (loveClick != null) {
				reviewDto.getContent().get(i).setLoveClick(true);
			} else {
				reviewDto.getContent().get(i).setLoveClick(false);
			}

			// 해당 리뷰 상세 내용
			DetailReviewDTO detailDto = detailReviewRepository.fineByRid(rid);
			reviewDto.getContent().get(i).setDetail(detailDto);

			// 해당 리뷰 댓글 (최신순 3개)
			List<CommentDTO> commentDto = commentRepository.findByRid(rid, PageRequest.of(0, 3, Sort.Direction.DESC, "cid"));
			reviewDto.getContent().get(i).setComments(commentDto);

		}

		Map<String, Object> res = new TreeMap<>();
		res.put("reviewList", reviewDto.getContent());
		res.put("totalCnt", reviewDto.getTotalElements());

		return res;
	}
	
	// 댓글 조회
	public List<CommentDTO> getComment(int rid, int commentPage) {
		
		// 오름차순
		List<CommentDTO> commentDto = commentRepository.findByRid(rid, PageRequest.of(commentPage, 20,  Sort.Direction.ASC, "cid"));

		return commentDto;
	}

	// 리뷰 작성
	public WrapperDTO addReview(int aid, int id, ReviewWriteDTO reveiwWriteDto, HttpServletResponse response)
			throws ParseException, IOException {
		ReviewVO reviewVo = new ReviewVO();
		DetailReviewVO detailVo = new DetailReviewVO();

		AlcoholVO alcoholChk = alcoholRepository.findByAid(aid);

		if (alcoholChk == null) {
			response.sendError(404, "Alcohol Id Error");
			return null;
		}
		
		// 날짜 확인 후 리뷰 중복 조회
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String dateToStr = dateFormat.format(date);
		
		ReviewVO chk = reviewRepository.findById(id, dateToStr);

		System.out.println("CHK : " + chk);
		if(chk != null) {
			response.sendError(403, "Writing Restriction");
			return null;
		}
		
		reviewVo.setId(id);
		reviewVo.setAid(aid);
		reviewVo.setStar(reveiwWriteDto.getStar());
		reviewVo.setContent(reveiwWriteDto.getContent());
		int rid = reviewRepository.save(reviewVo).getRid();

		if (reveiwWriteDto.getDetail() == null) {
			return null;
		} else { // 상세 리뷰 있으면 상세 리뷰 저장
			detailVo.setRid(rid);
			detailVo.setDate(reveiwWriteDto.getDetail().getDate());
			detailVo.setPlace(reveiwWriteDto.getDetail().getPlace());
			detailVo.setDrink(reveiwWriteDto.getDetail().getDrink());
			detailVo.setHangover(reveiwWriteDto.getDetail().getHangover());
			detailVo.setPrice(reveiwWriteDto.getDetail().getPrice());
			detailVo.setPrivacy(reveiwWriteDto.getDetail().isPrivacy());

			detailReviewRepository.save(detailVo);
		}

		WrapperDTO dto = new WrapperDTO("success");

		return dto;

	}

	// 댓글 작성
	public WrapperDTO addComment(int id, int rid, ContentDTO content, HttpServletResponse response) throws IOException {
		if (reviewChk(rid) == 0) {
			response.sendError(404, "Review Id Error");
			return null;
		}

		CommentVO commentVo = new CommentVO();
		commentVo.setId(id);
		commentVo.setRid(rid);
		commentVo.setContent(content.getContent());
		commentRepository.save(commentVo);

		WrapperDTO dto = new WrapperDTO("success");

		return dto;
	}

	// 리뷰 신고
	public WrapperDTO reportReview(int id, int rid, ContentDTO content, HttpServletResponse response)
			throws IOException {
		if (reviewChk(rid) == 0) {
			response.sendError(404, "Review Id Error");
			return null;
		}

		ReportReviewVO reportVo = new ReportReviewVO();
		reportVo.setRid(rid);
		reportVo.setId(id);
		reportVo.setContent(content.getContent());
		reportReviewRepository.save(reportVo);
		reviewRepository.updateReport(rid);

		WrapperDTO dto = new WrapperDTO("success");

		return dto;
	}

	// 댓글 신고
	public WrapperDTO reportComment(int id, int cid, ContentDTO content, HttpServletResponse response)
			throws IOException {
		Optional<CommentVO> commentChk = commentRepository.findById(cid);

		if (commentChk.isPresent() == false) {
			response.sendError(404, "Comment Id Error");
			return null;
		}

		ReportCommentVO reportVo = new ReportCommentVO();
		reportVo.setCid(cid);
		reportVo.setId(id);
		reportVo.setContent(content.getContent());
		reportCommentRepository.save(reportVo);
		commentRepository.updateReport(cid);

		WrapperDTO dto = new WrapperDTO("success");

		return dto;
	}

	// 리뷰 좋아요
	public WrapperDTO reviewLove(int id, int rid, HttpServletResponse response) throws SQLIntegrityConstraintViolationException, IOException{

		if(reviewLoveRepository.insert(id, rid)==1) {
			reviewRepository.updateLove(rid);
		}
		WrapperDTO dto = new WrapperDTO("Review Love Success");

		return dto;
	}

	// 리뷰 좋아요 취소
	public WrapperDTO reviewLoveCancle(int id, int rid, HttpServletResponse response) throws IOException {
		
		if(reviewLoveRepository.delete(id, rid)==1) {
			reviewRepository.updateLoveCancle(rid);
		}
		WrapperDTO dto = new WrapperDTO("Review Love Cancle Success");

		return dto;
	}

	// 리뷰 id 확인
	public int reviewChk(int rid) {
		int result = 0;

		if(reviewRepository.findById(rid).isPresent() == true) {
			result = 1;
		}

		return result;
	}

}
