package com.doubleslash.fifth.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.doubleslash.fifth.service.AuthService;
import com.doubleslash.fifth.service.CabinetService;
import com.doubleslash.fifth.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Api(value = "Cabinet", description = "장식장 API")
@RestController
@RequestMapping(value = "/cabinet")
@RequiredArgsConstructor
public class CabinetController {
	
	private final AuthService authService;
	private final UserService userService;
	private final CabinetService cabinetService;
	
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
	public ResponseEntity<?> drinkAlcohol(@RequestParam("page") int page, @RequestParam("sort") String sort, @RequestParam("sortOption") String sortOption, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uid = authService.verifyToken(request);
		Long id = userService.getId(uid);

		if(!sort.equals("latest") && !sort.equals("abv")) {
			ErrorMessage error = new ErrorMessage("Wrong Sort input");
			return new ResponseEntity<ErrorMessage>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		if(!sortOption.equals("asc") && !sortOption.equals("desc")) {
			ErrorMessage error = new ErrorMessage("Wrong SortOption input");
			return new ResponseEntity<ErrorMessage>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		return ResponseEntity.ok(cabinetService.getDrinkAlcohol(id, page, sort, sortOption));
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
	public ResponseEntity<?> loveAlcohol(@RequestParam("page") int page, @RequestParam("sort") String sort, @RequestParam("sortOption") String sortOption, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uid = authService.verifyToken(request);
		Long id = userService.getId(uid);

		if(!sort.equals("latest") && !sort.equals("abv")) {
			ErrorMessage error = new ErrorMessage("Wrong Sort input");
			return new ResponseEntity<ErrorMessage>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		if(!sortOption.equals("asc") && !sortOption.equals("desc")) {
			ErrorMessage error = new ErrorMessage("Wrong SortOption input");
			return new ResponseEntity<ErrorMessage>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		return ResponseEntity.ok(cabinetService.getLoveAlcohol(id, page, sort, sortOption));
	}
	
	@ApiOperation(value = "마시고 싶은 술 삭제")
	@ApiImplicitParam(name = "aid", required = true, paramType = "path", value = "여러 개의 aid는 콤마(,)로 구분 \n ex) 1, 2, 3")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Success"),
	})
	@DeleteMapping(value = "/love/{aid}")
	public ResponseEntity<?> deleteLoveAlcohol(@PathVariable List<Long> aid, HttpServletRequest request) throws Exception {
		String uid = authService.verifyToken(request);
		Long id = userService.getId(uid);

		cabinetService.deleteLoveAlcohol(id, aid);
		return ResponseEntity.ok().build();
	}
	
	@Data
	@AllArgsConstructor
	private static class ErrorMessage {
		private String message;
	}
 
}