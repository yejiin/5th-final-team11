package com.doubleslash.fifth.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doubleslash.fifth.dto.RatingDTO;
import com.doubleslash.fifth.repository.AlcoholRepository;
import com.doubleslash.fifth.repository.RatingRepository;
import com.doubleslash.fifth.vo.AlcoholVO;

@Service
public class RatingService {
	
	@Autowired
	AlcoholRepository alcoholRepository;
	
	@Autowired
	RatingRepository ratingRepository;

	private final Integer[] RATING_TARGET_AID_LIST = new Integer[]{6,7,8,9,10,11,12,13,14,15,16};
	
	public List<RatingDTO.Response> getRatingTargetList(){

		List<Integer> aidList = Arrays.asList(RATING_TARGET_AID_LIST);
		Collections.shuffle(aidList);
		aidList = aidList.subList(0, 10);

		List<RatingDTO.Response> res = new ArrayList<RatingDTO.Response>();
		AlcoholVO temp;
		
		for(int aid : aidList) {
			temp = alcoholRepository.findByAid(aid);
			res.add(new RatingDTO.Response(temp.getAid(), temp.getImage(), temp.getName(), temp.getCategory(), 0.0));
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
