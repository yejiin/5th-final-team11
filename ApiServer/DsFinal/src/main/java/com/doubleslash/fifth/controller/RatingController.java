package com.doubleslash.fifth.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doubleslash.fifth.dto.RatingDTO;
import com.doubleslash.fifth.dto.WrapperDTO;
import com.doubleslash.fifth.service.AuthService;
import com.doubleslash.fifth.service.RatingService;
import com.doubleslash.fifth.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
@RequestMapping(value = "/alcohol")
@Api(value = "Recommend", description = "주류 평가 API")
public class RatingController {
	
	@Autowired
	AuthService authService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	RatingService ratingService;

	@ApiOperation(value = "평가 데이터 10개 조회")
	@ApiResponses({		
		@ApiResponse(code = 200, message = "Get 10 Rating Data"),
	})
	@GetMapping(value = "/rating")
	@ResponseBody
	public WrapperDTO getRating() {
		return new WrapperDTO(ratingService.getRatingTargetList());
	}
	
	@ApiOperation(value = "평가 데이터 저장")
	@ApiResponses({		
		@ApiResponse(code = 201, message = "Create Rating Data"),
	})
	@PostMapping(value = "/rating")
	@ResponseBody
	public String createRating(@RequestBody RatingDTO requestBody, HttpServletRequest request) throws Exception{
		String uid = authService.verifyToken(request);
		int id = userService.getId(uid);

		ratingService.createRatingData(29, requestBody);
		return "{}";
	}
	
}
