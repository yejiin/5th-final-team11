package com.doubleslash.fifth.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doubleslash.fifth.dto.ContentDTO;
import com.doubleslash.fifth.dto.ReviewDTO;
import com.doubleslash.fifth.dto.ReviewWriteDTO;
import com.doubleslash.fifth.dto.WrapperDTO;
import com.doubleslash.fifth.service.AuthService;
import com.doubleslash.fifth.service.ReviewService;
import com.doubleslash.fifth.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Api(value = "Review", description = "리뷰, 댓글 작성 API")
@Controller
@RequestMapping(value = "/review")
public class ReviewController {
	
	@Autowired
	AuthService authService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ReviewService reviewService;
	
	@ApiOperation(value = "리뷰 리스트 조회", notes="rid(리뷰id), nickname(닉네임), content(내용), love(하트 수), loveClick(하트 클릭 여부), reviewDate(리뷰작성 날짜), detail(상세 리뷰), comments(댓글 리스트)")
	@ApiImplicitParam(name = "aid", required = true, dataType = "int", paramType = "query", value = "알코올 id")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Success"),
		@ApiResponse(code = 404, message = "Alcohol Id Error")
	})
	@GetMapping(value = "")
	@ResponseBody
	public List<ReviewDTO> reviewList(@RequestParam("aid") int aid, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uid = authService.verifyToken(request);
		int id = userService.getId(uid);

		List<ReviewDTO> dto = new ArrayList<ReviewDTO>();
		dto = reviewService.getReviewList(aid, id, response);
		
		return dto;
	}
	
	
	@ApiOperation(value = "리뷰 작성", notes="상세 기록 안 적을 시 \"detail\": null 로 하여 requestBody에 포함")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Success"),
		@ApiResponse(code = 404, message = "Alcohol Id Error")
	})
	@PostMapping(value ="")
	@ResponseBody
	public void reviewWrite(@RequestParam("aid") int aid, @RequestBody ReviewWriteDTO requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uid = authService.verifyToken(request);
		int id = userService.getId(uid);
	
		reviewService.addReview(aid, id, requestBody, response);
	}
	
	
	@ApiOperation(value = "댓글 작성", notes="rid : 리뷰 id")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Success"),
		@ApiResponse(code = 404, message = "Review Id Error")
	})
	@PostMapping(value = "/comment")
	@ResponseBody
	public WrapperDTO commentWrite(@RequestParam("rid") int rid, @RequestBody ContentDTO content, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uid = authService.verifyToken(request);
		int id = userService.getId(uid);
	
		WrapperDTO dto = reviewService.addComment(id, rid, content, response);
		return dto;
	}
	
	
	@ApiOperation(value = "리뷰 신고", notes="rid : 리뷰 id")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Success"),
		@ApiResponse(code = 404, message = "Review Id Error")
	})
	@PostMapping(value = "/report/{rid}")
	@ResponseBody
	public WrapperDTO reviewReport(@PathVariable("rid") int rid, @RequestBody ContentDTO content, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uid = authService.verifyToken(request);
		int id = userService.getId(uid);
	
		WrapperDTO dto = reviewService.reportReview(id, rid, content, response);
		return dto;
	}
	
	
	@ApiOperation(value = "댓글 신고", notes="cid : 댓글 id")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Success"),
		@ApiResponse(code = 404, message = "Comment Id Error")
	})
	@PostMapping(value = "/comment/report/{cid}")
	@ResponseBody
	public WrapperDTO commentReport(@PathVariable("cid") int cid, @RequestBody ContentDTO content, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uid = authService.verifyToken(request);
		int id = userService.getId(uid);

		WrapperDTO dto = reviewService.reportComment(id, cid, content, response);
		return dto;
	}
	
	
	@ApiOperation(value = "리뷰 좋아요")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Success"),
		@ApiResponse(code = 404, message = "Review Id Error")
	})
	@PostMapping(value = "/love/{rid}")
	@ResponseBody
	public WrapperDTO reviewLove(@PathVariable int rid, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uid = authService.verifyToken(request);
		int id = userService.getId(uid);

		WrapperDTO dto = reviewService.loveReview(id, rid, response);
		return dto;
	}
	
}
