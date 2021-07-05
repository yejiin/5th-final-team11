package com.doubleslash.fifth.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doubleslash.fifth.dto.LoveClickDTO;
import com.doubleslash.fifth.dto.response.LoveResponse;
import com.doubleslash.fifth.service.AlcoholService;
import com.doubleslash.fifth.service.AuthService;
import com.doubleslash.fifth.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@Api(value = "Alcohol", description = "주류 상세 조회 API" )
@RestController
@RequestMapping(value = "/alcohol")
@RequiredArgsConstructor
public class AlcoholController {

	private final AuthService authService;
	private final AlcoholService alcoholService;
	private final UserService userService;
	
	@ApiOperation(value = "주류 세부 사항 조회", notes="{\r\n"
			+ "aid : int\r\n"
			+ "name : String\r\n"
			+ "category : String\r\n"
			+ "image : String\r\n"
			+ "lowestPrice : int\r\n"
			+ "highestPrice : int\r\n"
			+ "ml : int\r\n"
			+ "abv : float\r\n"
			+ "description : String\r\n"
			+ "kind : String\r\\n"
			+ "starAvg : float\r\n"
			+ "startCnt : int\r\n"
			+ "loveClick : boolean\r\n"
			+ "userDrink : String  (로그인 안했으면 null)\r\n"
//			+ "similar : List({ aId : int, aImage : String, aName: String })\r\n"
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
	public ResponseEntity<?> detail(@PathVariable("aid") Long aid, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uid = authService.verifyToken(request);
		Long id;
		
		if(uid == null) {
			id = -1L;
			return ResponseEntity.ok(alcoholService.findAlcoholForGuest(aid).getData());
		}else {
			id = userService.getId(uid);
			return ResponseEntity.ok(alcoholService.findAlcohol(id, aid).getData());
		}
	}
	
	@ApiOperation(value = "주류 찜하기", notes = "true : 찜하기, false : 찜하기 취소")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Success"),
		@ApiResponse(code = 404, message = "Alcohol Id Error")
	})
	@PutMapping(value = "/{aid}/love")
	public ResponseEntity<LoveResponse> alcoholLove(@PathVariable Long aid, @RequestBody LoveClickDTO loveClick, HttpServletRequest request) throws Exception {
		String uid = authService.verifyToken(request);
		Long id = userService.getId(uid);

		if(loveClick.isLoveClick()) {
			return ResponseEntity.ok(alcoholService.addLove(id, aid));
		} else if(!loveClick.isLoveClick()) {
			return ResponseEntity.ok(alcoholService.cancelLove(id, aid));
		}

		return ResponseEntity.badRequest().build();
	}
}
