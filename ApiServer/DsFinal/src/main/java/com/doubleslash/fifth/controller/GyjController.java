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
import com.doubleslash.fifth.service.GYJ_RecommendService;
import com.doubleslash.fifth.service.RecommendService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@Api(value = "GYJ", description = "윤종님을 위한 추천 테스트 API" )
@Controller
@RequestMapping(value = "/GYJ")
public class GyjController {
	
	@Autowired
	GYJ_RecommendService gyj_recommendService;
	
	@ApiImplicitParams({
        @ApiImplicitParam(name = "category", required = true, dataType = "string", paramType = "query",
        		example = "전체",
        		value = "전체 / 양주 / 와인 / 세계맥주")
	})
	@PostMapping(value = "")
	@ResponseBody
	public WrapperDTO getRecommend(@RequestBody RecommendDTO requestBody,HttpServletRequest request) throws Exception{
		String category = request.getParameter("category");

		gyj_recommendService.createRecommend(requestBody, 1234);	
		return new WrapperDTO(gyj_recommendService.getRecommend(1234, category, null, null));
	}

}
