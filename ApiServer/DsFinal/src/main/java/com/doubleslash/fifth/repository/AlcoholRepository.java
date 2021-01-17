package com.doubleslash.fifth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.doubleslash.fifth.dto.BeerDTO;
import com.doubleslash.fifth.dto.LiquorDTO;
import com.doubleslash.fifth.dto.WineDTO;
import com.doubleslash.fifth.vo.AlcoholVO;

@Repository
public interface AlcoholRepository extends JpaRepository<AlcoholVO, Integer>{

	public AlcoholVO findByAid(int aid);
	
	public List<AlcoholVO> findByCategory(String category);
	
	// 양주 조회
	@Query(value = "select new com.doubleslash.fifth.dto.LiquorDTO(a.aid, a.name, a.category, a.image, a.lowestPrice, a.highestPrice, a.ml, a.abv, a.description, a.kind, l.flavor, AVG(r.star), COUNT(*)) from AlcoholVO as a, LiquorVO as l, ReviewVO as r where a.aid = l.aid and a.aid = r.aid and a.aid = ?1")
	public LiquorDTO findByAidLiquor(int aid);

	// 리뷰 존재하지 않을 때 양주 조회
	@Query(value = "select new com.doubleslash.fifth.dto.LiquorDTO(a.aid, a.name, a.category, a.image, a.lowestPrice, a.highestPrice, a.ml, a.abv, a.description, a.kind, l.flavor, 0.0, 0L) from AlcoholVO as a, LiquorVO as l where a.aid = l.aid and a.aid = ?1")
	public LiquorDTO findByAidLiquorNoReview(int aid);
	
	// 맥주 조회
	@Query(value = "select new com.doubleslash.fifth.dto.BeerDTO(a.aid, a.name, a.category, a.image, a.lowestPrice, a.highestPrice, a.ml, a.abv, a.description, a.kind, b.area, AVG(r.star), COUNT(*)) from AlcoholVO as a, BeerVO as b, ReviewVO as r where a.aid = b.aid and a.aid = r.aid and a.aid = ?1")
	public BeerDTO findByAidBeer(int aid);
	
	// 리뷰 존재하지 않을 때 맥주 조회
	@Query(value = "select new com.doubleslash.fifth.dto.BeerDTO(a.aid, a.name, a.category, a.image, a.lowestPrice, a.highestPrice, a.ml, a.abv, a.description, a.kind, b.area, 0.0, 0L) from AlcoholVO as a, BeerVO as b where a.aid = b.aid and a.aid = ?1")
	public BeerDTO findByAidBeerNoReview(int aid);

	// 와인 조회
	@Query(value = "select new com.doubleslash.fifth.dto.WineDTO(a.aid, a.name, a.category, a.image, a.lowestPrice, a.highestPrice, a.ml, a.abv, a.description, a.kind, w.country, w.area, w.flavor, w.body, AVG(r.star), COUNT(*)) from AlcoholVO as a, WineVO as w, ReviewVO as r where a.aid = w.aid and a.aid = r.aid and a.aid = ?1")
	public WineDTO findByAidWine(int aid);
	
	// 리뷰 존재하지 않을 때 와인 조회
	@Query(value = "select new com.doubleslash.fifth.dto.WineDTO(a.aid, a.name, a.category, a.image, a.lowestPrice, a.highestPrice, a.ml, a.abv, a.description, a.kind, w.country, w.area, w.flavor, w.body, 0.0, 0L) from AlcoholVO as a, WineVO as w where a.aid = w.aid and a.aid = ?1")
	public WineDTO findByAidWineNoReview(int aid);
}
