package com.doubleslash.fifth.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doubleslash.fifth.dto.ContentDTO;
import com.doubleslash.fifth.dto.LoveClickDTO;
import com.doubleslash.fifth.dto.ReviewWriteDTO;
import com.doubleslash.fifth.dto.WrapperDTO;
import com.doubleslash.fifth.service.AuthService;
import com.doubleslash.fifth.service.ReviewService;
import com.doubleslash.fifth.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
	@ApiImplicitParams({
		@ApiImplicitParam(name = "aid", required = true, dataType = "int", paramType = "query", example = "1", value = "알코올 id"),
		@ApiImplicitParam(name = "page", required = true, dataType = "int", paramType = "query", 
			example = "0", value = "페이지 번호(페이지당 데이터 20개)")
	})
		@ApiResponses({
		@ApiResponse(code = 200, message = "Success"),
		@ApiResponse(code = 404, message = "Alcohol Id Error")
	})
	@GetMapping(value = "")
	@ResponseBody
	public Map<String, Object> reviewList(@RequestParam("aid") int aid, @RequestParam("page") int page, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uid = authService.verifyToken(request);
		int id = userService.getId(uid);

		return reviewService.getReviewList(aid, page, id, response);
	}
	
	
	@ApiOperation(value = "리뷰 작성", notes="상세 기록 안 적을 시 \"detail\": null 로 하여 requestBody에 포함")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Success"),
		@ApiResponse(code = 404, message = "Alcohol Id Error")
	})
	@PostMapping(value ="")
	@ResponseBody
	public WrapperDTO reviewWrite(@RequestParam("aid") int aid, @RequestBody ReviewWriteDTO requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uid = authService.verifyToken(request);
		int id = userService.getId(uid);
	
		WrapperDTO dto = reviewService.addReview(aid, id, requestBody, response);
		return dto;
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
	@PutMapping(value = "/{rid}/report")
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
	@PutMapping(value = "/comment/{cid}/report")
	@ResponseBody
	public WrapperDTO commentReport(@PathVariable("cid") int cid, @RequestBody ContentDTO content, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uid = authService.verifyToken(request);
		int id = userService.getId(uid);

		WrapperDTO dto = reviewService.reportComment(id, cid, content, response);
		return dto;
	}
	
	
	@ApiOperation(value = "리뷰 좋아요", notes = "true : 리뷰 좋아요, false : 리뷰 좋아요 취소")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Success"),
		@ApiResponse(code = 404, message = "Review Id Error")
	})
	@PutMapping(value = "/{rid}/love")
	@ResponseBody
	public WrapperDTO reviewLove(@PathVariable int rid, @RequestBody LoveClickDTO loveClick, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uid = authService.verifyToken(request);
		int id = userService.getId(uid);

		WrapperDTO dto = new WrapperDTO();
		
		if(loveClick.getLoveClick() == true) {
			dto = reviewService.reviewLove(id, rid, response);
		}else {
			dto = reviewService.reviewLoveCancle(id, rid, response);
		}

		return dto;
	}
	
}
