package com.doubleslash.fifth.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.doubleslash.fifth.dto.RatingDTO;
import com.doubleslash.fifth.repository.AlcoholRepository;
import com.doubleslash.fifth.repository.RatingRepository;
import com.doubleslash.fifth.vo.AlcoholVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RatingService {

	private final AlcoholRepository alcoholRepository;
	private final RatingRepository ratingRepository;

	private final Integer[] RATING_TARGET_AID_LIST = new Integer[]
			{36,36,47,51,55,62,65,68,69,71,72,77,80,81,84,85,90,91,94,95,100,
			1,2,6,7,11,12,16,17,21,22,26,27};
	
	public List<RatingDTO.Response> getRatingTargetList(){

		List<Integer> aidList = Arrays.asList(RATING_TARGET_AID_LIST);
		Collections.shuffle(aidList);
		aidList = aidList.subList(0, 10);

		List<RatingDTO.Response> res = new ArrayList<RatingDTO.Response>();
		AlcoholVO temp;
		
		for(int aid : aidList) {
			temp = alcoholRepository.findByAid(aid);
			res.add(new RatingDTO.Response(temp.getAid(), temp.getThumbnail(), temp.getName(), temp.getCategory(), 0.0));
		}

		return res;
	}

	public void createRatingData(int id, RatingDTO requestBody) {
		List<RatingDTO.Request> request = requestBody.getRatingData();
		
		for(RatingDTO.Request data : request) {
			ratingRepository.insert(id, data.getAid(), data.getStar());
		}
		
	}
	
}
