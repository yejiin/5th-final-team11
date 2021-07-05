package com.doubleslash.fifth.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.doubleslash.fifth.dto.CabinetDTO;
import com.doubleslash.fifth.dto.response.CabinetResponse;
import com.doubleslash.fifth.entity.alcohol.AlcoholLove;
import com.doubleslash.fifth.repository.AlcoholLoveRepository;
import com.doubleslash.fifth.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CabinetService {

	private final ReviewRepository reviewRepository;
	private final AlcoholLoveRepository alcoholLoveRepository;
	
	public CabinetResponse<CabinetDTO> getDrinkAlcohol(Long id, int page, String sort, String sortOption) {
		if(!sort.equals("abv")) {
			sort = "createdDate";
		} else {
			sort = "alcohol.abv";
		}
		
		Page<CabinetDTO> cabinet = reviewRepository.findByUserIdOrderBy(id, PageRequest.of(page, 20, dirOption(sortOption), sort));

		return new CabinetResponse<CabinetDTO>(cabinet.toList(), cabinet.getTotalElements());
	
	}
	
	public CabinetResponse<CabinetDTO> getLoveAlcohol(Long id, int page, String sort, String sortOption) {
		if(!sort.equals("abv")) {
			sort = "createdDate";
		} else {
			sort = "alcohol.abv";
		}
		
		Page<CabinetDTO> cabinet = alcoholLoveRepository.findByUserIdOrderBy(id, PageRequest.of(page, 20, dirOption(sortOption), sort));
		
		return new CabinetResponse<CabinetDTO>(cabinet.toList(), cabinet.getTotalElements());
	}
	
	@Transactional
	public void deleteLoveAlcohol(Long id, List<Long> aid) {
		
		for(Long i : aid) {
			AlcoholLove alcoholLove = alcoholLoveRepository.findByUserIdAndAlcoholId(id, i);
			alcoholLoveRepository.delete(alcoholLove);
		}
		
	}
	
	//정렬 기준별 오름차순, 내림차순 구분
	private Direction dirOption(String sortOption) {
		if(sortOption.equals("desc")) return Sort.Direction.DESC;
		return Sort.Direction.ASC;
	}
}