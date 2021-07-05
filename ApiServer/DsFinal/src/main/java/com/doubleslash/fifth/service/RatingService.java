package com.doubleslash.fifth.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.doubleslash.fifth.dto.RatingDTO;
import com.doubleslash.fifth.entity.alcohol.Alcohol;
import com.doubleslash.fifth.repository.AlcoholRepository;
import com.doubleslash.fifth.repository.RatingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RatingService {

	private final AlcoholRepository alcoholRepository;
	private final RatingRepository ratingRepository;

	private final Long[] RATING_TARGET_AID_LIST = new Long[]
			{36L,36L,47L,51L,55L,62L,65L,68L,69L,71L,72L,77L,80L,81L,84L,85L,90L,91L,94L,95L,100L,
			1L,2L,6L,7L,11L,12L,16L,17L,21L,22L,26L,27L};
	
	public List<RatingDTO.Response> getRatingTargetList(){

		List<Long> aidList = Arrays.asList(RATING_TARGET_AID_LIST);
		Collections.shuffle(aidList);
		aidList = aidList.subList(0, 10);

		List<RatingDTO.Response> res = new ArrayList<RatingDTO.Response>();
		Alcohol temp;
		
		for(Long aid : aidList) {
			temp = alcoholRepository.findById(aid).get();
			res.add(new RatingDTO.Response(temp.getId(), temp.getThumbnail(), temp.getName(), temp.getCategory(), 0.0f));
		}

		return res;
	}

	public void createRatingData(Long id, RatingDTO requestBody) {
		List<RatingDTO.Request> request = requestBody.getRatingData();
		
		for(RatingDTO.Request data : request) {
			ratingRepository.insert(id, data.getAid(), data.getStar());
		}
		
	}
	
}
