package com.doubleslash.fifth.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.doubleslash.fifth.dto.AlcoholSearchDTO;
import com.doubleslash.fifth.dto.RecommendDTO;
import com.doubleslash.fifth.repository.AlcoholRepository;
import com.doubleslash.fifth.repository.RecommendRepository;
import com.doubleslash.fifth.repository.SearchRepository;
import com.doubleslash.fifth.storage.BeerStorage;
import com.doubleslash.fifth.storage.LiquorStorage;
import com.doubleslash.fifth.storage.WineStorage;
import com.doubleslash.fifth.vo.AlcoholVO;
import com.doubleslash.fifth.vo.RecommendVO;
import com.doubleslash.fifth.vo.ViewSearchVO;

@Service
public class RecommendService {

	@Autowired
	RecommendRepository recommendRepository;
	
	@Autowired
	SearchRepository searchRepository;
	
	@Autowired
	AlcoholRepository alcoholRepository;
	
	//가중치
	private final int FLAVOR_WEIGHT = 66;
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
		
		int recCb = 2;
		if(rec.getCb().equals("유")) recCb = 1;
		else recCb = 0;
		int cbWeight = CB_WEIGHT;
		
		//int celeb; 인지도
		
		//스코어링 Map
		HashMap<Integer, Integer> score = new HashMap<>();
		
		RecommendDTO.liquor liquor = rec.getAlcohol().getLiquor();
		if(liquor != null) {
			List<LiquorStorage> liquorCompTarget = alcoholRepository.AlcoholJoinLiquor();

			LiquorStorage target;
			String recKind = liquor.getKind();
			String recFlavor = liquor.getFlavor();
			
			for(int i = 0; i < liquorCompTarget.size(); i++) {
				target = liquorCompTarget.get(i); //타겟 설정
				int aid = target.getAid();
				score.put(aid, 0); //스코어링 데이터 삽입
				
				//맛 스코어링
				String kind = target.getKind();
				String flavor[] = target.getFlavor().split("#");
				
				int flavorWeight = FLAVOR_WEIGHT;
				
				if(recKind.contains(kind)) {
					int point = 0;
					for(String f : flavor) {
						if(recFlavor.contains(f)) point += 1;
					}
					if(point == 0) flavorWeight = FLAVOR_WEIGHT - 15;
					else if(point >= 2) flavorWeight = FLAVOR_WEIGHT + 10;
				}else {
					flavorWeight = FLAVOR_WEIGHT - 15;
				}
				score.put(aid, score.get(aid) + flavorWeight);	
				
				double abv = target.getAbv();
				int price = (target.getHighestPrice() + target.getLowestPrice())/2;
				int cb = target.getCb();
				
				//도수 스코어링
				if(minAbv <= abv && abv <= maxAbv) 
					score.put(aid, score.get(aid) + abvWeight);
				//가격 스코어링
				if(minPrice <= price && price <= maxPrice)
					score.put(aid, score.get(aid) + priceWeight);
				//탄산여부 스코어링
				if(recCb == cb)
					score.put(aid, score.get(aid) + cbWeight);				
			}
		}
		
		RecommendDTO.wine wine = rec.getAlcohol().getWine();
		if(wine != null) {
			List<WineStorage> wineCompTarget = alcoholRepository.AlcoholJoinWine();

			WineStorage target;
			String recKind = wine.getKind();
			int recMinFlavor = wine.getFlavor()-1;
			int recMaxFlavor = wine.getFlavor()+1;
			int recMinBody = wine.getBody()-1;
			int recMaxBody = wine.getBody()+1;
			
			for(int i = 0; i < wineCompTarget.size(); i++) {
				target = wineCompTarget.get(i); //타겟 설정
				int aid = target.getAid();
				score.put(aid, 0); //스코어링 데이터 삽입
				
				//맛 스코어링
				String kind = target.getKind();
				int flavor = target.getFlavor();
				int body = target.getBody();
				
				int flavorWeight = FLAVOR_WEIGHT;
				
				if(!recKind.equals(kind)) {
					flavorWeight = 0;
				}else {
					if((recMinFlavor <= flavor && flavor <= recMaxFlavor) && (recMinBody <= body && body <= recMaxBody)) flavorWeight += 10;
					else if(!((recMinFlavor <= flavor && flavor <= recMaxFlavor) && (recMinBody <= body && body <= recMaxBody))) flavorWeight -= 15;
				}
				score.put(aid, score.get(aid) + flavorWeight);	
				
				double abv = target.getAbv();
				int price = (target.getHighestPrice() + target.getLowestPrice())/2;
				int cb = target.getCb();
				
				//도수 스코어링
				if(minAbv <= abv && abv <= maxAbv) 
					score.put(aid, score.get(aid) + abvWeight);
				//가격 스코어링
				if(minPrice <= price && price <= maxPrice)
					score.put(aid, score.get(aid) + priceWeight);
				//탄산여부 스코어링
				if(recCb == cb)
					score.put(aid, score.get(aid) + cbWeight);				
			}
		}
		RecommendDTO.beer beer = rec.getAlcohol().getBeer();
		if(beer != null) {
			List<BeerStorage> beerCompTarget = alcoholRepository.AlcoholJoinBeer();

			BeerStorage target;
			
			String recKind = beer.getKind().getMainKind();
			String recSubKind = beer.getKind().getSubKind();
			String recArea = beer.getArea();
			
			for(int i = 0; i < beerCompTarget.size(); i++) {
				target = beerCompTarget.get(i); //타겟 설정
				int aid = target.getAid();
				score.put(aid, 0); //스코어링 데이터 삽입
				
				//맛 스코어링
				String kind = target.getKind();
				String subKind = target.getSubKind();
				String area = target.getArea();
				
				int flavorWeight = FLAVOR_WEIGHT;
				
				if(!recKind.contains(kind)) {
					flavorWeight = 0;
				}else {
					if(!recSubKind.contains(subKind)) {
						flavorWeight -= 7.5;
					}
				}
				
				if(recArea.contains(area)) flavorWeight += 5;
				
				score.put(aid, score.get(aid) + flavorWeight);	
				
				double abv = target.getAbv();
				int price = (target.getHighestPrice() + target.getLowestPrice())/2;
				int cb = target.getCb();
				
				//도수 스코어링
				if(minAbv <= abv && abv <= maxAbv) 
					score.put(aid, score.get(aid) + abvWeight);
				//가격 스코어링
				if(minPrice <= price && price <= maxPrice)
					score.put(aid, score.get(aid) + priceWeight);
				//탄산여부 스코어링
				if(recCb == cb)
					score.put(aid, score.get(aid) + cbWeight);				
			}
		}

		//스코어링된 주류를 스코어기준 내림차순으로 전부 정렬함
		List<Integer> sortedAid = descending(score);
		
		try {
			recommendRepository.deleteById(id); //기존 추천데이터 삭제
		}catch(Exception e) {
			
		}
		
		//새로운 추천데이터 생성
		for(int i = 0; i < 6; i++) {
			int aid = sortedAid.get(i);
			int recScore = score.get(aid);
			recommendRepository.insert(id, aid, recScore);
		}
	}
	
	public List<AlcoholSearchDTO> getRecommend(int id, String sort, String sortOption) {
		if(sort == null) sort = "recScore";
		if(sortOption == null) sortOption = "desc";
		
		List<AlcoholSearchDTO> res = searchRepository.findById(id, sortOption(dirOption(sortOption), sort));
		
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
