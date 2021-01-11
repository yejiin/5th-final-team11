package com.doubleslash.fifth.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doubleslash.fifth.service.AlcoholService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Alcohol", description = "Alcohol API" )
@Controller
@RequestMapping(value = "/alcohol")
public class AlcoholController {
	
	@Autowired
	AlcoholService alcoholService;

	@ApiOperation(value = "주류 세부 사항 조회", notes="주류 공통 속성 \n: aid(주류id), name(주류명), category(카테고리), image(이미지경로), lowestPrice(최저가격), highest(최고가격), ml(용량), abv(도수), description(설명), kind(종류, list 타입), starAvg(별점평균), starCnt(별점수), userDrink(사용자주량), smiliar(비슷한 술 정보)"
			+ "\n 주류별 추가 속성 \n: 양주 - flavor(맛, list 타입), \n  맥주 - area(지역, list 타입), \n  와인 - country(국가), area(지역), town(상세지역), wineKind(품종), flavor(맛, int 타입(1~5로 구분)), body(바디감, int 타입(1~5로 구분))"
			+ "\n 추천사항 정보인 종류, 맛, 지역은 list로 제공")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Alcohol Information Get Succues "),		
		@ApiResponse(code = 400, message = "Alcohol Id Error"),
	})
	@GetMapping(value = "/detail/{id}")
	@ResponseBody
	public String detail(@PathVariable("id") int id, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
	
		String category = alcoholService.getCategory(id);
		
		String result ="";
		if(category == null) {
			response.sendError(400, "Alcohol Id Error");
		}else {
			response.setStatus(200);
			if(category.equals("양주")) {
				map = alcoholService.getLiquor(id);
			}else if(category.equals("세계맥주")) {
				map = alcoholService.getBeer(id);
			}else if(category.equals("와인")) {
				map = alcoholService.getWine(id);
			}
			JSONObject jsonObject = new JSONObject(map);
			result = jsonObject.toJSONString();
		}
		
		return result;
	}
}
