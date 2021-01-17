package com.doubleslash.fifth.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.doubleslash.fifth.dto.AlcoholSearchDTO;
import com.doubleslash.fifth.dto.RecommendDTO;
import com.doubleslash.fifth.repository.AlcoholRepository;
import com.doubleslash.fifth.repository.RecommendRepository;
import com.doubleslash.fifth.repository.SearchRepository;
import com.doubleslash.fifth.vo.AlcoholVO;
import com.doubleslash.fifth.vo.RecommendVO;

@Service
public class RecommendService {

	@Autowired
	RecommendRepository recommendRepository;
	
	@Autowired
	SearchRepository searchRepository;
	
	@Autowired
	AlcoholRepository alcoholRepository;
	
	//가중치
	private final int ABV_WEIGHT = 41;
	private final int PRICE_WEIGHT = 40;
	private final int CB_WEIGHT = 10;
	
	public void createRecommend(RecommendDTO rec, int id){
		double minAbv = rec.getMinAbv();
		double maxAbv = calcAbv(rec.getMaxAbv());
		int abvWeight = calcAbvWeight(rec.getMaxAbv() - rec.getMinAbv());
		
		int minPrice = rec.getMinPrice();
		int maxPrice = calcPrice(rec.getMaxPrice());
		int priceWeight = calcPriceWeight(rec.getMaxPrice() - rec.getMinPrice());
		
		String cb = rec.getCb();
		//int celeb; 인지도
		
		//스코어링 Map
		HashMap<Integer, Integer> score = new HashMap<>();
		
		RecommendDTO.liquor liquor = rec.getAlcohol().getLiquor();
		if(liquor != null) {
			List<AlcoholVO> liquorCompTarget = alcoholRepository.findByCategory("양주");
			HashMap<Integer, Integer> liquorScore = new HashMap<>();

			AlcoholVO target;
			double abv;
			int price;
			
			for(int i = 0; i < liquorCompTarget.size(); i++) {
				target = liquorCompTarget.get(i); //타겟 설정
				int aid = target.getAid();
				liquorScore.put(aid, 0); //스코어링 데이터 삽입
				
				abv = target.getAbv(); //도수
				price = (target.getHighestPrice() + target.getLowestPrice())/2; //가격
				
				//도수 스코어링
				if(minAbv <= abv && abv <= maxAbv) 
					liquorScore.put(aid, liquorScore.get(aid) + abvWeight);
				//가격 스코어링
				if(minPrice <= price && price <= maxPrice)
					liquorScore.put(aid, liquorScore.get(aid) + priceWeight);
//				if(target.getCb().equals(cb))
//					score[i] += CB_WEIGHT;
					
			}
		}
		RecommendDTO.wine wine = rec.getAlcohol().getWine();
		if(wine != null) {
			
		}
		RecommendDTO.beer beer = rec.getAlcohol().getBeer();
		if(beer != null) {
			
		}

		//스코어링된 주류를 스코어기준 내림차순으로 전부 정렬함
		List<Integer> sortedAid = descending(score);
		
		recommendRepository.deleteById(id); //기존 추천데이터 삭제
		
		//새로운 추천데이터 생성
		for(int i = 0; i < 10; i++) {
			int aid = sortedAid.get(i);
			int recScore = score.get(aid);
			RecommendVO vo = new RecommendVO(id, aid, recScore);
			recommendRepository.save(vo);
		}
	}
	
	public List<AlcoholSearchDTO> getRecommend(int id, String sort) {
		if(sort == null) sort = "recScore";
		
		//Recommend와 ViewSearch를 Join하고 정렬기준을 추천/리뷰/별점으로 설정하여 List로 가져온다.
		//메소드는 SearchRepository에 NativeQuery를 사용하여 구현한다.
		
		return null;
	}
	
	//내림차순 정렬
	public List<Integer> descending(HashMap<Integer, Integer> data) {
		List<Integer> sortedAid = new ArrayList<>(data.keySet());
        Collections.sort(sortedAid, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return data.get(o2).compareTo(data.get(o1));
			}
        });
        return sortedAid;
	}
	
	//도수 상한값 재설정
	public double calcAbv(double maxAbv) {
		if(maxAbv <= 5) {
			return maxAbv;
		}else if(maxAbv <= 12) {
			return maxAbv + 2;
		}else if(maxAbv <= 22) {
			return maxAbv + 5;
		}else {
			return 80;
		}
	}
	
	//도수 가중치 재설정
	public int calcAbvWeight(double abvGap) {
		if(abvGap < 10) {
			return ABV_WEIGHT + 15;
		}else if(abvGap < 30) {
			return ABV_WEIGHT;
		}else {
			return ABV_WEIGHT - 15;
		}
	}
	
	//가격 상한가 재설정
	public int calcPrice(int maxPrice) {
		if(maxPrice <= 50000) {
			return (int) (maxPrice*1.1);
		}else if(maxPrice <= 200000) {
			return (int) (maxPrice*1.2);
		}else {
			return 400000;
		}
	}
	
	//가격 가중치 재설정
	public int calcPriceWeight(int priceGap) {
		if(priceGap < 50000) {
			return PRICE_WEIGHT + 20;
		}else if(priceGap <= 150000) {
			return PRICE_WEIGHT + 10;
		}else {
			return PRICE_WEIGHT;
		}
	}
	
}
