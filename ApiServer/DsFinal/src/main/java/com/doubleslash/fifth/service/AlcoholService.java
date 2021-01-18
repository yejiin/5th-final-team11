package com.doubleslash.fifth.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doubleslash.fifth.dto.BeerDTO;
import com.doubleslash.fifth.dto.LiquorDTO;
import com.doubleslash.fifth.dto.WineDTO;
import com.doubleslash.fifth.repository.AlcoholRepository;
import com.doubleslash.fifth.repository.ReviewRepository;
import com.doubleslash.fifth.repository.UserRepository;
import com.doubleslash.fifth.vo.AlcoholVO;
import com.doubleslash.fifth.vo.UserVO;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AlcoholService {
	
	@Autowired
	AlcoholRepository alcoholRepository;
	
	@Autowired
	ReviewRepository reviewRepository;
	
	@Autowired
	UserRepository userRepository;
	
	// category 조회
	public String getCategory(int aid) {
		Optional<AlcoholVO> vo = alcoholRepository.findById(aid);
		String category = "";
		if(vo.isPresent()) {
			category = vo.get().getCategory();
		}else {
			category = null;
		}
		return category;
		
	}
	
	// 양주 주종 세부 조회
	public Map<String, Object> getLiquor(int id, int aid){
		ObjectMapper objectMapper = new ObjectMapper();
		LiquorDTO liquorDto = alcoholRepository.findByAidLiquor(aid);
			
		Map<String, Object> liquorMap = objectMapper.convertValue(liquorDto, Map.class);
		liquorMap.put("kind", getKinds(liquorDto.getKind()));
		
		// 맛 데이터 가공
		List<String> flavors = new ArrayList<String>();
		String ftemp[] = liquorDto.getFlavors().split("#");
		for(int i = 0; i < ftemp.length; i++) {
			flavors.add(ftemp[i]);
		}
		liquorMap.put("flavors", flavors);
		liquorMap.put("userDrink", getUserDrink(id, aid));
		
		return liquorMap;
	}
	
	// 세계 맥주 주종 세부 조회
	public Map<String, Object> getBeer(int id,int aid) {
		ObjectMapper objectMapper = new ObjectMapper();
		BeerDTO beerDto = alcoholRepository.findByAidBeer(aid);

		Map<String, Object> beerMap = objectMapper.convertValue(beerDto, Map.class);
		beerMap.put("kind", getKinds(beerDto.getKind()));
		
		// 지역 데이터 가공
		List<String> areas = new ArrayList<String>();
		String atemp[] = beerDto.getAreas().split("#");
		for(int i = 0; i < atemp.length; i++) {
			areas.add(atemp[i]);
		}
		beerMap.put("areas", areas);
		beerMap.put("userDrink", getUserDrink(id, aid));
		
		return beerMap;
	}
	
	// 와인 주종 세부 조회
	public Map<String, Object> getWine(int id, int aid) {
		ObjectMapper objectMapper = new ObjectMapper();
		WineDTO wineDto = alcoholRepository.findByAidWine(aid);
		
		Map<String, Object> wineMap = objectMapper.convertValue(wineDto, Map.class);
		
		wineMap.put("kind", getKinds(wineDto.getKind()));
		wineMap.put("userDrink", getUserDrink(id, aid));
		return wineMap;
	}
	

	// 종류(공통 속성) 데이터 가공
	public List<String> getKinds(String kind){
		List<String> kinds = new ArrayList<String>();
		String ktemp[] = kind.split("#");
		for(int i = 0; i < ktemp.length; i++) {
			kinds.add(ktemp[i]);
		}
		return kinds;		
	}
	
	// 주종별 사용자 주량 
	public double getUserDrink(int id, int aid) {
		Optional<UserVO> userVo = userRepository.findById(id);
		
		// 소주 기준 
		double sojuDrink = userVo.get().getDrink();
		double sojuAbv = 20.0;
		
		// 알콜량 = 소주병수*도수*용량/100   
		double sojuAmount = (sojuDrink*sojuAbv*360/100);
	
		AlcoholVO alcoholVo = alcoholRepository.findByAid(aid);
		double alcoholAbv = alcoholVo.getAbv();
		int alcoholMl = alcoholVo.getMl();
		double alcoholAmount = (alcoholAbv*alcoholMl/100);
		
		double userDrink = Math.round(sojuAmount / alcoholAmount*10)/10.0;
		System.out.println("사용자 주량 : " + userDrink);
		return userDrink;
	}
	
	
}
