package com.doubleslash.fifth.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doubleslash.fifth.dto.LoveClickDTO;
import com.doubleslash.fifth.service.AlcoholService;
import com.doubleslash.fifth.service.AuthService;
import com.doubleslash.fifth.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Alcohol", description = "주류 상세 조회 API" )
@Controller
@RequestMapping(value = "/alcohol")
public class AlcoholController {
	
	@Autowired
	AuthService authService;
	
	@Autowired
	AlcoholService alcoholService;

	@Autowired
	UserService userService;
	
	@ApiOperation(value = "주류 세부 사항 조회", notes="{\r\n"
			+ "aid : int\r\n"
			+ "name : String\r\n"
			+ "category : String\r\n"
			+ "image : String\r\n"
			+ "lowestPrice : int\r\n"
			+ "highestPrice : int\r\n"
			+ "ml : int\r\n"
			+ "abv : double\r\n"
			+ "description : String\r\n"
			+ "kind : String"
			+ "starAvg : double  \r\n"
			+ "startCnt : int\r\n"
			+ "loveClick : boolean\r\n"
			+ "userDrink : String  (로그인 상태에서만 보임)\r\n"
			+ "similar : List({ aId : int, aImage : String, aName: String })\r\n"
			+ "}\r\n"
			+ "\r\n"
			+ "양주 + flavors : List<String>\r\n"
			+ "맥주 + flavors : List<String>, subKind : String\r\n"
			+ "와인 + country : String , area : String , flavor : int, body : int")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Alcohol Information Get Succues "),		
		@ApiResponse(code = 400, message = "Alcohol Id Error"),
	})
	@ApiImplicitParam(name = "Authorization", value = "idToken", required = false, paramType = "header")
	@GetMapping(value = "/{aid}", produces = "application/json; charset=utf8")
	@ResponseBody
	public String detail(@PathVariable("aid") int aid, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uid = authService.verifyToken(request);
		int id;
		
		if(uid == null) {
			id = -1;
		}else {
			id = userService.getId(uid);
		}

		Map<String, Object> map = new HashMap<String, Object>();
	
		String category = alcoholService.getCategory(aid);
		
		String result ="";

		if(category == null) {
			response.sendError(400, "Alcohol Id Error");
		}else {
			response.setStatus(200);
			if(category.equals("양주")) {
				map = alcoholService.getLiquor(id, aid);
			}else if(category.equals("세계맥주")) {
				map = alcoholService.getBeer(id, aid);
			}else if(category.equals("와인")) {
				map = alcoholService.getWine(id, aid);
			}
			JSONObject jsonObject = new JSONObject(map);
			result = jsonObject.toJSONString();
		}
		
		return result;
	}
	
	@ApiOperation(value = "주류 찜하기", notes = "true : 찜하기, false : 찜하기 취소")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Success"),
		@ApiResponse(code = 404, message = "Alcohol Id Error")
	})
	@PutMapping(value = "/{aid}/love")
	@ResponseBody
	public Map<String, Object> alcoholLove(@PathVariable int aid, @RequestBody LoveClickDTO loveClick, HttpServletRequest request) throws Exception {
		String uid = authService.verifyToken(request);
		int id = userService.getId(uid);

		Map<String, Object> res = new TreeMap<>();
		
		if(loveClick.isLoveClick()) {
			res = alcoholService.alcoholLove(id, aid);
		} else if(!loveClick.isLoveClick()) {
			res = alcoholService.alcoholLoveCancle(id, aid);
		}
		
		return res;

	}
}
