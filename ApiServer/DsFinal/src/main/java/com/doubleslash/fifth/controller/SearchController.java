package com.doubleslash.fifth.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doubleslash.fifth.dto.WrapperDTO;
import com.doubleslash.fifth.service.SearchService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;

@Controller
@RequestMapping(value = "/alcohol")
@Api(value = "Alcohol", description = "검색 API")
public class SearchController {
	
	@Autowired
	SearchService searchService;
	
	@ApiOperation(value = "주류 조회 & 정렬")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "category", required = true, dataType = "string", paramType = "query",
        		example = "전체",
        		value = "전체 / 양주 / 와인 / 세계맥주"),
        @ApiImplicitParam(name = "page", required = true, dataType = "string", paramType = "query",
				example = "0",
				value = "페이지 번호(페이지당 데이터 20개)"),
        @ApiImplicitParam(name = "sort", required = true, dataType = "string", paramType = "query", 
        		example = "popularScore",
        		value = "인기순 : popularScore(default) / desc\n"
        				+ "별점순 : star / desc\n"
        				+ "찜한순 : loveCnt / desc\n"
        				+ "도수순 : abv / asc\n"
        				+ "가격순 : price / asc, desc"),
        @ApiImplicitParam(name = "sortOption", required = true, dataType = "string", paramType = "query",
				example = "desc",
				value = "asc / desc")
	})
	@ApiResponses({
		@ApiResponse(code = 200, message = "Success"),
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping
	@ResponseBody
	public Map<String, Object> basicSearch(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String category = request.getParameter("category");
		String sort = request.getParameter("sort");
		String sortOption = request.getParameter("sortOption");
		int page = Integer.parseInt(request.getParameter("page"));
		if(category == null) {
			response.sendError(400, "Bad Request");
			return null;
		}
		return searchService.basicSearch(category, sort, sortOption, page);
	}
	
	
	@ApiOperation(value = "키워드로 주류 검색 & 정렬")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "keyword", required = true, dataType = "string", paramType = "query",
        		example = "주류 이름",
        		value = "주류 이름"),
        @ApiImplicitParam(name = "page", required = true, dataType = "string", paramType = "query",
				example = "0",
				value = "페이지 번호(페이지당 데이터 20개)"),
        @ApiImplicitParam(name = "sort", required = true, dataType = "string", paramType = "query", 
				example = "popularScore",
				value = "인기순 : popularScore(default) / desc\n"
						+ "별점순 : star / desc\n"
						+ "찜한순 : loveCnt / desc\n"
						+ "도수순 : abv / asc\n"
						+ "가격순 : price / asc, desc"),
        @ApiImplicitParam(name = "sortOption", required = true, dataType = "string", paramType = "query",
        		example = "desc",
        		value = "asc / desc")
	})
	@ApiResponses({
		@ApiResponse(code = 200, message = "Success"),
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/search")
	@ResponseBody
	public Map<String, Object> keywordSearch(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String keyword = request.getParameter("keyword");
		String sort = request.getParameter("sort");
		String sortOption = request.getParameter("sortOption");
		int page = Integer.parseInt(request.getParameter("page"));
		if(keyword == null) {
			response.sendError(400, "Bad Request");
			return null;
		}
		return searchService.keywordSearch(keyword, sort, sortOption, page);
	}
	
}
