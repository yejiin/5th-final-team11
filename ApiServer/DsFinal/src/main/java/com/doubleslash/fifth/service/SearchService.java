package com.doubleslash.fifth.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.doubleslash.fifth.dto.AlcoholSearchDTO;
import com.doubleslash.fifth.mapping.SearchMapping;
import com.doubleslash.fifth.repository.SearchRepository;

@Service
public class SearchService {
	
	@Autowired
	SearchRepository searchRepository;
	
	public Map<String, Object> basicSearch(String category, String sort, String sortOption, int page){
		if(sort == null) sort = "popularScore";
		if(sortOption == null) sortOption = "desc";
		
		List<AlcoholSearchDTO> dto = new ArrayList<AlcoholSearchDTO>();
		Page<SearchMapping> mapping;
		if(category.equals("전체")){
			mapping = searchRepository.findByAidIsNotNull(PageRequest.of(page, 20, sortOption(dirOption(sortOption), sort)));
		}else {
			mapping = searchRepository.findByCategory(category, PageRequest.of(page, 20, sortOption(dirOption(sortOption), sort)));
		}
		
		for (SearchMapping m : mapping) {
			dto.add(AlcoholSearchDTO.builder()
					.aid(m.getAid())
					.name(m.getName())
					.category(m.getCategory())
					.star(m.getStar())
					.reviewCnt(m.getReviewCnt())
					.build()
					);
		};
		
		Map<String, Object> res = new TreeMap<>();
		res.put("searchList", dto);
		res.put("totalCnt", mapping.getTotalElements());
		
		return res;
	}
	
	public Map<String, Object> keywordSearch(String keyword, String sort, String sortOption, int page){
		if(sort == null) sort = "popularScore";
		if(sortOption == null) sortOption = "desc";
		List<AlcoholSearchDTO> dto = new ArrayList<AlcoholSearchDTO>();
		Page<SearchMapping> mapping = searchRepository.findByNameContaining(keyword, PageRequest.of(page, 20, sortOption(dirOption(sortOption), sort)));
		
		for (SearchMapping m : mapping) {
			dto.add(AlcoholSearchDTO.builder()
					.aid(m.getAid())
					.name(m.getName())
					.category(m.getCategory())
					.reviewCnt(m.getReviewCnt())
					.star(m.getStar())
					.build()
					);
		}	
		
		Map<String, Object> res = new TreeMap<>();
		res.put("searchList", dto);
		res.put("totalCnt", mapping.getTotalElements());
		
		return res;
	}
	
	//정렬 기준을 동적으로 설정
	private Sort sortOption(Direction direction, String property) {
		List<Order> orders = new ArrayList<Sort.Order>();
		orders.add(new Order(direction, property));
		orders.add(new Order(Direction.ASC, "name"));
		return Sort.by(orders);
	}
	
	//정렬 기준별 오름차순, 내림차순 구분
	private Direction dirOption(String sortOption) {
		if(sortOption.equals("desc")) return Sort.Direction.DESC;
		return Sort.Direction.ASC;
	}

}

