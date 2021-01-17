package com.doubleslash.fifth.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
        @ApiImplicitParam(name = "requestBody", required = true, dataType = "RecommendDTO", paramType = "body",
        		value = "여러개의 문자열 데이터는 #으로 구분하여 전송\n"
        				+ "ex)레드와인#화이트와인#로제와인\n"
        				+ "추천대상으로 선택하지 않은 주류 데이터는 제외하고 전송\n"
        				+ "ex)맥주와 양주만 선택했을 경우 'wine' 제거")
	})
	@ApiResponses({		
		@ApiResponse(code = 400, message = "Bad Request"),
		@ApiResponse(code = 401, message = "Unauthorized")
	})
	@PostMapping(value = "/recommend")
	@ResponseBody
	public WrapperDTO recommend(@RequestBody RecommendDTO requestBody, HttpServletRequest request) throws Exception{
		String uid = authService.verifyToken(request);
		int id = userService.getId(uid);
		
		String sort = request.getParameter("sort");
		recommendService.createRecommend(requestBody, id);
		
		return new WrapperDTO(recommendService.getRecommend(id, sort));
	}
	
}
