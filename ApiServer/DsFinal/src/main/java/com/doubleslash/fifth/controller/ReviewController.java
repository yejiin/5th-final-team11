package com.doubleslash.fifth.controller;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	
	@ApiOperation(value = "리뷰 조회", notes="리뷰 당 최신 댓글 데이터 3개 제공\n"
			+ "로그인 안했을 시 loveClick 전부 false")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "aid", required = true, dataType = "int", paramType = "query", example = "1", value = "알코올 id"),
		@ApiImplicitParam(name = "page", required = true, dataType = "int", paramType = "query", example = "0", value = "페이지 번호(페이지당 데이터 20개)"),
	})
		@ApiResponses({
		@ApiResponse(code = 200, message = "Success"),
		@ApiResponse(code = 404, message = "Alcohol Id Error")
	})
	@ApiImplicitParam(name = "Authorization", value = "idToken", required = false, paramType = "header")
	@GetMapping(value = "/list")
	@ResponseBody
	public Map<String, Object> reviewList(@RequestParam("aid") int aid, @RequestParam(value="page") int page, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uid = authService.verifyToken(request);
		int id;
		
		if(uid == null) {
			id = -1;
		}else {
			id = userService.getId(uid);
		}

		return reviewService.getReviewList(aid, page, id, response);
	}
	
	@ApiOperation(value = "댓글 조회")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "rid", required = true, dataType = "int", paramType = "query", example = "1", value = "리뷰 id"),
		@ApiImplicitParam(name = "page", required = true, dataType = "int", paramType = "query", example = "0", value = "페이지 번호(페이지당 데이터 20개)"),
	})
	@GetMapping(value ="/comment")
	@ResponseBody
	public Map<String, Object> commentList(@RequestParam("rid") int rid, @RequestParam("page") int page) {
		
		return reviewService.getComment(rid, page);
	}
	
	
	@ApiOperation(value = "리뷰 작성", notes="상세 기록 안 적을 시 \"detail\": null 로 하여 requestBody에 포함, 일일 1회 리뷰 작성 제한")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Success"),
		@ApiResponse(code = 403, message = "Writing Restriction"),
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
	@PostMapping(value = "/{rid}/comment")
	@ResponseBody
	public Map<String, Object> commentWrite(@PathVariable("rid") int rid, @RequestBody ContentDTO content, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uid = authService.verifyToken(request);
		int id = userService.getId(uid);
		
		return reviewService.addComment(id, rid, content, response);
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
	public Map<String, Object> reviewLove(@PathVariable int rid, @RequestBody LoveClickDTO loveClick, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uid = authService.verifyToken(request);
		int id = userService.getId(uid);
		
		Map<String, Object> res = new TreeMap<>();
		
		if(loveClick.isLoveClick()) {
			res = reviewService.reviewLove(id, rid, response);
		}else if(!loveClick.isLoveClick()) {
			res = reviewService.reviewLoveCancle(id, rid, response);
		}

		return res;
	}
	
	@ApiOperation(value = "내 기록 조회")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "page", required = true, dataType = "string", paramType = "query",
				example = "0",
				value = "페이지 번호(페이지당 데이터 20개)"),
        @ApiImplicitParam(name = "sort", required = true, dataType = "string", paramType = "query", 
        		example = "latest",
        		value = "시간순 : latest(default) / desc\n"
        				+ "도수순 : abv / desc\n"),
        @ApiImplicitParam(name = "sortOption", required = true, dataType = "string", paramType = "query",
				example = "desc",
				value = "asc / desc")
	})
	@ApiResponses({
		@ApiResponse(code = 200, message = "Success"),
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "")
	@ResponseBody
	public Map<String, Object> getMyReviewList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uid = authService.verifyToken(request);
		int id = userService.getId(uid);
		
		String sort = request.getParameter("sort");
		String sortOption = request.getParameter("sortOption");
		int page = Integer.parseInt(request.getParameter("page"));
		
		return reviewService.getMyReviewList(id, sort, sortOption, page);
	}
	
	@ApiOperation(value = "내 기록 수정")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Success"),
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PutMapping(value = "/{rid}")
	@ResponseBody
	public String updateMyReviewList(HttpServletRequest request, HttpServletResponse response, @RequestBody ReviewWriteDTO requestBody, @PathVariable int rid) throws Exception {
		String uid = authService.verifyToken(request);
		int id = userService.getId(uid);
		
		reviewService.updateMyReview(requestBody, id, rid);
		return "{}";
	}
	
	@ApiOperation(value = "내 기록 삭제")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Success"),
	})
	@DeleteMapping(value = "/{rid}")
	@ResponseBody
	public String DeleteMyReviewList(@PathVariable List<Integer> rid, HttpServletRequest request) throws Exception {		
		reviewService.deleteMyReview(rid);
		return "{}";
	}
	
}
