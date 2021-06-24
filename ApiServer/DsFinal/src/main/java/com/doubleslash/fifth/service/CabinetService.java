package com.doubleslash.fifth.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.doubleslash.fifth.dto.CabinetDTO;
import com.doubleslash.fifth.repository.AlcoholLoveRepository;
import com.doubleslash.fifth.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CabinetService {

	private final ReviewRepository reviewRepository;
	private final AlcoholLoveRepository alcoholLoveRepository;
	
	public Map<String, Object> getDrinkAlcohol(int id, int page, String sort, String sortOption) {
		if(!sort.equals("abv")) sort = "create_time";
		
		Page<CabinetDTO> cabinetDto;
		
		if(sort.equals("create_time")) {			
			cabinetDto = reviewRepository.findDrinkAlcoholOrderTime(id, PageRequest.of(page, 20, dirOption(sortOption), sort));
		}else {			
			cabinetDto = reviewRepository.findDrinkAlcoholOrderAbv(id, PageRequest.of(page, 20, dirOption(sortOption), "abv"));
		}
		
		Map<String, Object> res = new TreeMap<>();
		res.put("AlcoholList", cabinetDto.getContent());
		res.put("totalCnt", cabinetDto.getTotalElements());
		
		return res;
	
	}
	
	public Map<String, Object> getLoveAlcohol(int id, int page, String sort, String sortOption) {
		if(!sort.equals("abv")) sort = "create_time";
		
		Page<CabinetDTO> cabinetDto;
		
		if(sort.equals("create_time")) {
			cabinetDto = alcoholLoveRepository.findLoveAlcoholOrderTime(id, PageRequest.of(page, 20, dirOption(sortOption), sort));
		}else {
			cabinetDto = alcoholLoveRepository.findLoveAlcoholOrderAbv(id, PageRequest.of(page, 20, dirOption(sortOption), "abv"));
		}
		
		Map<String, Object> res = new TreeMap<>();
		res.put("AlcoholList", cabinetDto.getContent());
		res.put("totalCnt", cabinetDto.getTotalElements());
		
		return res;
			
	}
	
	@Transactional
	public void deleteLoveAlcohol(int id, List<Integer> aid) {
		
		for(int i : aid) {
			alcoholLoveRepository.delete(id, i);
		}
		
	}
	
	//정렬 기준을 동적으로 설정
	private Sort sortOption(Direction direction, String property) {
		List<Order> orders = new ArrayList<Sort.Order>();
		orders.add(new Order(direction, property));
		return Sort.by(orders);
	}
	
	//정렬 기준별 오름차순, 내림차순 구분
	private Direction dirOption(String sortOption) {
		if(sortOption.equals("desc")) return Sort.Direction.DESC;
		return Sort.Direction.ASC;
	}
}