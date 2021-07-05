package com.doubleslash.fifth.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.doubleslash.fifth.dto.CommentDTO;
import com.doubleslash.fifth.dto.ContentDTO;
import com.doubleslash.fifth.dto.LoveClickDTO;
import com.doubleslash.fifth.dto.MyReviewDTO;
import com.doubleslash.fifth.dto.ReviewDTO;
import com.doubleslash.fifth.dto.ReviewWriteDTO;
import com.doubleslash.fifth.dto.WrapperDTO;
import com.doubleslash.fifth.dto.response.CommentResponse;
import com.doubleslash.fifth.dto.response.LoveResponse;
import com.doubleslash.fifth.dto.response.ReviewResponse;
import com.doubleslash.fifth.service.AuthService;
import com.doubleslash.fifth.service.ReviewService;
import com.doubleslash.fifth.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;


@Api(value = "Review", description = "리뷰, 댓글 작성 API")
@RestController
@RequestMapping(value = "/review")
@RequiredArgsConstructor
public class ReviewController {

	private final AuthService authService;
	private final UserService userService;
	private final ReviewService reviewService;
	
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
	public ResponseEntity<ReviewResponse<ReviewDTO>> reviewList(@RequestParam("aid") Long aid, @RequestParam(value="page") int page, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uid = authService.verifyToken(request);
		Long id;
		
		if(uid == null) {
			id = -1L;
		}else {
			id = userService.getId(uid);
		}

		return ResponseEntity.ok(reviewService.getReviews(aid, page, id));
	}
	
	@ApiOperation(value = "리뷰 작성", notes="상세 기록 안 적을 시 \"detail\": null 로 하여 requestBody에 포함, 일일 1회 리뷰 작성 제한")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Success"),
		@ApiResponse(code = 403, message = "Writing Restriction"),
		@ApiResponse(code = 404, message = "Alcohol Id Error")
	})
	@PostMapping(value ="")
	public ResponseEntity<WrapperDTO> reviewWrite(@RequestParam("aid") Long aid, @RequestBody ReviewWriteDTO requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uid = authService.verifyToken(request);
		Long id = userService.getId(uid);

		String res = reviewService.writeReview(aid, id, requestBody);
		if (res == null) {
			return new ResponseEntity(HttpStatus.FORBIDDEN);
		}
		return ResponseEntity.ok(new WrapperDTO(res));
	}
	
	@ApiOperation(value = "리뷰 좋아요", notes = "true : 리뷰 좋아요, false : 리뷰 좋아요 취소")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Success"),
		@ApiResponse(code = 404, message = "Review Id Error")
	})
	@PutMapping(value = "/{rid}/love")
	public ResponseEntity<LoveResponse> reviewLove(@PathVariable Long rid, @RequestBody LoveClickDTO loveClick, HttpServletRequest request) throws Exception {
		String uid = authService.verifyToken(request);
		Long id = userService.getId(uid);

		if(loveClick.isLoveClick()) {
			return ResponseEntity.ok(reviewService.addLoveReview(id, rid));
		}else if(!loveClick.isLoveClick()) {
			return ResponseEntity.ok(reviewService.cancleLoveReview(id, rid));
		}

		return ResponseEntity.badRequest().build();
	}
	
	@ApiOperation(value = "리뷰 신고", notes="rid : 리뷰 id")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Success"),
		@ApiResponse(code = 404, message = "Review Id Error")
	})
	@PutMapping(value = "/{rid}/report")
	public ResponseEntity<WrapperDTO> reviewReport(@PathVariable("rid") Long rid, @RequestBody ContentDTO content, HttpServletRequest request) throws Exception {
		String uid = authService.verifyToken(request);
		Long id = userService.getId(uid);

		return ResponseEntity.ok(new WrapperDTO(reviewService.reportReview(id, rid, content.getContent())));
	}
	
	@ApiOperation(value = "댓글 조회")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "rid", required = true, dataType = "int", paramType = "query", example = "1", value = "리뷰 id"),
		@ApiImplicitParam(name = "page", required = true, dataType = "int", paramType = "query", example = "0", value = "페이지 번호(페이지당 데이터 20개)"),
	})
	@GetMapping(value ="/comment")
	public ResponseEntity<CommentResponse> comments(@RequestParam("rid") Long rid, @RequestParam("page") int page) {
		
		return ResponseEntity.ok(reviewService.getComments(rid, page));
	}
	
	@ApiOperation(value = "댓글 작성", notes="rid : 리뷰 id")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Success"),
		@ApiResponse(code = 404, message = "Review Id Error")
	})
	@PostMapping(value = "/{rid}/comment")
	public CommentDTO commentWrite(@PathVariable("rid") Long rid, @RequestBody ContentDTO content, HttpServletRequest request) throws Exception {
		String uid = authService.verifyToken(request);
		Long id = userService.getId(uid);
		
		return reviewService.writeComment(id, rid, content.getContent());
	}
	
		
	@ApiOperation(value = "댓글 신고", notes="cid : 댓글 id")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Success"),
		@ApiResponse(code = 404, message = "Comment Id Error")
	})
	@PutMapping(value = "/comment/{cid}/report")
	public ResponseEntity<WrapperDTO> commentReport(@PathVariable("cid") Long cid, @RequestBody ContentDTO content, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uid = authService.verifyToken(request);
		Long id = userService.getId(uid);

		return ResponseEntity.ok(new WrapperDTO(reviewService.reportComment(id, cid, content.getContent())));
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
	public ResponseEntity<ReviewResponse<MyReviewDTO>> getMyReviewList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uid = authService.verifyToken(request);
		Long id = userService.getId(uid);
		
		String sort = request.getParameter("sort");
		String sortOption = request.getParameter("sortOption");
		int page = Integer.parseInt(request.getParameter("page"));
		
		return ResponseEntity.ok(reviewService.getMyReviewList(id, sort, sortOption, page));
	}
	
	@ApiOperation(value = "내 기록 삭제")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Success"),
	})
	@DeleteMapping(value = "/{rid}")
	public ResponseEntity<?> DeleteMyReviewList(@PathVariable List<Long> rid, HttpServletRequest request) throws Exception {		
		reviewService.deleteMyReview(rid);
		
		return ResponseEntity.ok().build();
	}
	
	@ApiOperation(value = "내 기록 수정")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Success"),
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PatchMapping(value = "/{rid}")
	public ResponseEntity<?> updateMyReview(HttpServletRequest request, HttpServletResponse response, @RequestBody ReviewWriteDTO reviewWriteDTO, @PathVariable Long rid) throws Exception {
		String uid = authService.verifyToken(request);
		Long id = userService.getId(uid);

		reviewService.updateMyReview(reviewWriteDTO, id, rid);
		return ResponseEntity.ok().build();
	}	
}
