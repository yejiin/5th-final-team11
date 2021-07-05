package com.doubleslash.fifth.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doubleslash.fifth.dto.RatingDTO;
import com.doubleslash.fifth.dto.WrapperDTO;
import com.doubleslash.fifth.service.AuthService;
import com.doubleslash.fifth.service.RatingService;
import com.doubleslash.fifth.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@Api(value = "Recommend", description = "주류 평가 API")
@RestController
@RequestMapping(value = "/alcohol")
@RequiredArgsConstructor
public class RatingController {

	private final AuthService authService;
	private final UserService userService;
	private final RatingService ratingService;

	@ApiOperation(value = "평가 데이터 10개 조회")
	@ApiResponses({		
		@ApiResponse(code = 200, message = "Get 10 Rating Data"),
	})
	@GetMapping(value = "/rating")
	public WrapperDTO getRating() {
		return new WrapperDTO(ratingService.getRatingTargetList());
	}
	
	@ApiOperation(value = "평가 데이터 저장")
	@ApiResponses({		
		@ApiResponse(code = 201, message = "Create Rating Data"),
	})
	@PostMapping(value = "/rating")
	public String createRating(@RequestBody RatingDTO requestBody, HttpServletRequest request) throws Exception{
		String uid = authService.verifyToken(request);
		Long id = userService.getId(uid);

		ratingService.createRatingData(id, requestBody);
		return "{}";
	}
	
}
