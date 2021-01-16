package com.doubleslash.fifth.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
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
	public List<ReviewDTO> getReviewList(int aid, int id, HttpServletResponse response) throws IOException {

		List<ReviewDTO> reviewListDto = reviewRepository.findByAid(aid);

		AlcoholVO alcoholChk = alcoholRepository.findByAid(aid);
		
		if(alcoholChk == null) {
			response.sendError(404, "Alcohol Id Error");
			return null;
		}
		for(int i = 0; i < reviewListDto.size(); i++) { 
			int rid = reviewListDto.get(i).getRid();

			// 해당 리뷰 하트 클릭 여부 확인
			ReviewLoveVO loveClick = reviewLoveRepository.findByIdRid(id, rid);

			if(loveClick != null) {
				reviewListDto.get(i).setLoveClick(true);
			}else {
				reviewListDto.get(i).setLoveClick(false);
			}

			
			// 해당 리뷰 상세 내용
			DetailReviewDTO detailDto = detailReviewRepository.fineByRid(rid);
			reviewListDto.get(i).setDetail(detailDto);

			
			// 해당 리뷰 댓글
			List<CommentDTO> commentDto = commentRepository.findByRid(rid);

			if(commentDto != null) {
				reviewListDto.get(i).setComments(commentDto);
			}
		} 

		return reviewListDto;
	}

	
	// 리뷰 작성
	public void addReview(int aid, int id, ReviewWriteDTO dto, HttpServletResponse response) throws ParseException, IOException {
		ReviewVO reviewVo = new ReviewVO();
		DetailReviewVO detailVo = new DetailReviewVO();
		
		AlcoholVO alcoholChk = alcoholRepository.findByAid(aid);
		
		if(alcoholChk == null) {
			response.sendError(404, "Alcohol Id Error");
			return;
		}

		reviewVo.setId(id);
		reviewVo.setAid(aid);
		reviewVo.setStar(dto.getStar());
		reviewVo.setContent(dto.getContent());
		int rid = reviewRepository.save(reviewVo).getRid();
		

		if(dto.getDetail() == null) {
			return;
		} else {	// 상세 리뷰 있으면 상세 리뷰 저장
			detailVo.setRid(rid);
			detailVo.setDate(dto.getDetail().getDate());
			detailVo.setPlace(dto.getDetail().getPlace());
			detailVo.setDrink(dto.getDetail().getDrink());
			detailVo.setHangover(dto.getDetail().getHangover());
			detailVo.setPrice(dto.getDetail().getPrice());
			detailVo.setSecurity(dto.getDetail().isSecurity());

			detailReviewRepository.save(detailVo);
		}

	}

	
	// 댓글 작성
	public WrapperDTO addComment(int id, int rid, ContentDTO content, HttpServletResponse response) throws IOException {
		if(reviewChk(rid) == 0) {
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
	public WrapperDTO reportReview(int id, int rid, ContentDTO content, HttpServletResponse response) throws IOException {
		if(reviewChk(rid) == 0) {
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
	public WrapperDTO reportComment(int id, int cid, ContentDTO content, HttpServletResponse response) throws IOException {
		Optional<CommentVO> commentChk = commentRepository.findById(cid);
		
		if(commentChk.isPresent() == false) {
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
	public WrapperDTO loveReview(int id, int rid, HttpServletResponse response) throws IOException {
		if(reviewChk(rid) == 0) {
			response.sendError(404, "Review Id Error");
			return null;
		}
		
		ReviewLoveVO loveVo = new ReviewLoveVO();
		loveVo.setId(id);
		loveVo.setRid(rid);
		reviewLoveRepository.save(loveVo);
		
		WrapperDTO dto = new WrapperDTO("success");
		
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
