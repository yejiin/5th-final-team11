package com.doubleslash.fifth.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doubleslash.fifth.dto.RecommendDTO;
import com.doubleslash.fifth.dto.WrapperDTO;
import com.doubleslash.fifth.service.AuthService;
import com.doubleslash.fifth.service.RecommendService;
import com.doubleslash.fifth.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
@RequestMapping(value = "/alcohol")
@Api(value = "Recommend", description = "추천 API")
public class RecommendController {
	
	@Autowired
	AuthService authService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	RecommendService recommendService;
	
	@ApiOperation(value = "주류 추천")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "category", required = true, dataType = "string", paramType = "query",
        		example = "전체",
        		value = "전체 / 양주 / 와인 / 세계맥주"),
        @ApiImplicitParam(name = "sort", required = true, dataType = "string", paramType = "query", 
        		example = "recScore",
        		value = "추천순 : recScore(default) / desc\n"
        				+ "리뷰순 : reviewCnt / desc\n"
        				+ "평점순 : star / desc\n"),
        @ApiImplicitParam(name = "sortOption", required = true, dataType = "string", paramType = "query",
				example = "desc",
				value = "asc / desc")
	})
	@ApiResponses({
		@ApiResponse(code = 200, message = "Success"),
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/recommend")
	@ResponseBody
	public WrapperDTO getRecommend(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String uid = authService.verifyToken(request);
		int id = userService.getId(uid);
		String category = request.getParameter("category");
		String sort = request.getParameter("sort");
		String sortOption = request.getParameter("sortOption");

		if(category == null) {
			response.sendError(400, "Bad Request");
			return null;
		}
		return new WrapperDTO(recommendService.getRecommend(id, sort, sortOption));
	}

	@ApiOperation(value = "추천 데이터 생성")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "requestBody", required = true, dataType = "RecommendDTO", paramType = "body",
        		value = "여러개의 문자열 데이터는 #으로 구분하여 전송\n"
        				+ "ex)레드와인#화이트와인#로제와인\n"
        				+ "추천대상으로 선택하지 않은 주류 데이터는 제외하고 전송\n"
        				+ "ex)맥주와 양주만 선택했을 경우 'wine' 제거\n"
        				+ "추천속성으로 선택하지 않은 데이터는 제외하고 전송\n"
        				+ "ex)와인에서 어떠한 종류도 선택하지 않았을 경우 'kind'제거")
	})
	@ApiResponses({		
		@ApiResponse(code = 400, message = "Bad Request"),
		@ApiResponse(code = 401, message = "Unauthorized")
	})
	@PostMapping(value = "/recommend")
	@ResponseBody
	public String createRecommend(@RequestBody RecommendDTO requestBody, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String uid = authService.verifyToken(request);
		int id = userService.getId(uid);

		recommendService.createRecommend(requestBody, id);		
		return "{}";
	}
	
}
