package com.doubleslash.fifth.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

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
	@ApiResponses({
		@ApiResponse(code = 200, message = "Success"),
		@ApiResponse(code = 422, message = "Wrong Sort input / Wrong SortOption input")
	})
	@GetMapping(value = "")
	@ResponseBody
	public Map<String, Object> drinkAlcohol(@RequestParam("page") int page, @RequestParam("sort") String sort, @RequestParam("sortOption") String sortOption, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uid = authService.verifyToken(request);
		int id = userService.getId(uid);

		if(!sort.equals("latest") && !sort.equals("abv")) {
			response.sendError(422, "Wrong Sort input");
			return null;
		}
		
		if(!sortOption.equals("asc") && !sortOption.equals("desc")) {
			response.sendError(422, "Wrong SortOption input");
			return null;
		}
		
		return cabinetService.getDrinkAlcohol(id, page, sort, sortOption);
	}
	
	@ApiOperation(value = "마시고 싶은 술 조회")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "page", required = true, dataType = "int", paramType = "query", example = "0", value = "페이지 번호(페이지당 데이터 20개)"),
		@ApiImplicitParam(name = "sort", required = true, dataType = "string", paramType = "query",
		example = "latest",
		value = "시간순 : latest \n"
				+ "도수순 : abv "),
		@ApiImplicitParam(name = "sortOption", required = true, dataType = "string", paramType = "query", example = "desc", value = "asc / desc")
	})
	@ApiResponses({
		@ApiResponse(code = 200, message = "Success"),
		@ApiResponse(code = 422, message = "Wrong Sort input / Wrong SortOption input")
	})
	@GetMapping(value = "/love")
	@ResponseBody
	public Map<String, Object> loveAlcohol(@RequestParam("page") int page, @RequestParam("sort") String sort, @RequestParam("sortOption") String sortOption, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uid = authService.verifyToken(request);
		int id = userService.getId(uid);
		
		if(!sort.equals("latest") && !sort.equals("abv")) {
			response.sendError(422, "Wrong Sort input");
			return null;
		}
		
		if(!sortOption.equals("asc") && !sortOption.equals("desc")) {
			response.sendError(422, "Wrong SortOption input");
			return null;
		}
		
		return cabinetService.getLoveAlcohol(id, page, sort, sortOption);
	}
	
	@ApiOperation(value = "마시고 싶은 술 삭제")
	@ApiImplicitParam(name = "aid", required = true, paramType = "path", value = "여러 개의 aid는 콤마(,)로 구분 \n ex) 1, 2, 3")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Success"),
	})
	@DeleteMapping(value = "/love/{aid}")
	@ResponseBody
	public String deleteLoveAlcohol(@PathVariable List<Integer> aid, HttpServletRequest request) throws Exception {
		String uid = authService.verifyToken(request);
		int id = userService.getId(uid);
		
		cabinetService.deleteLoveAlcohol(id, aid);
		return "{}";
	}
	
 
}