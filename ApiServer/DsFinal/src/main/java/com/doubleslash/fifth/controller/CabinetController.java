package com.doubleslash.fifth.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doubleslash.fifth.service.AuthService;
import com.doubleslash.fifth.service.CabinetService;
import com.doubleslash.fifth.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value = "Cabinet", description = "장식장 API")
@Controller
@RequestMapping(value = "/cabinet")
public class CabinetController {
	
	@Autowired
	AuthService authService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CabinetService cabinetService;
	
	@ApiOperation(value = "마신 술 조회")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "page", required = true, dataType = "int", paramType = "query", example = "0", value = "페이지 번호(페이지당 데이터 20개)"),
		@ApiImplicitParam(name = "sort", required = true, dataType = "string", paramType = "query",
		example = "latest",
		value = "시간순 : latest \n"
				+ "도수순 : abv "),
		@ApiImplicitParam(name = "sortOption", required = true, dataType = "string", paramType = "query", example = "desc", value = "asc / desc")
	})
	@GetMapping(value = "")
	@ResponseBody
	public Map<String, Object> drinkAlcohol(@RequestParam("page") int page, @RequestParam("sort") String sort, @RequestParam("sortOption") String sortOption, HttpServletRequest request) throws Exception {
		String uid = authService.verifyToken(request);
		int id = userService.getId(uid);
	
		return cabinetService.getDrinkAlcohol(id, page, sort, sortOption);
	}
	

}