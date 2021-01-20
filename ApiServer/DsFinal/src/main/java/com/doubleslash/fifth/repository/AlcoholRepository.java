package com.doubleslash.fifth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.doubleslash.fifth.dto.BeerDTO;
import com.doubleslash.fifth.dto.LiquorDTO;
import com.doubleslash.fifth.dto.SimilarAlcoholDTO;
import com.doubleslash.fifth.dto.WineDTO;
import com.doubleslash.fifth.vo.AlcoholVO;

@Repository
public interface AlcoholRepository extends JpaRepository<AlcoholVO, Integer>{

	public AlcoholVO findByAid(int aid);
	
	// 양주 조회
	@Query(value = "select new com.doubleslash.fifth.dto.LiquorDTO(a.aid, a.name, a.category, a.image, a.lowestPrice, a.highestPrice, a.ml, a.abv, a.description, a.kind, l.flavor, v.star, v.reviewCnt) from AlcoholVO as a, ViewSearchVO as v, LiquorVO as l where a.aid = v.aid and a.aid = l.aid and a.aid = ?1")
	public LiquorDTO findByAidLiquor(int aid);

	// 맥주 조회
	@Query(value = "select new com.doubleslash.fifth.dto.BeerDTO(a.aid, a.name, a.category, a.image, a.lowestPrice, a.highestPrice, a.ml, a.abv, a.description, a.kind, b.area, v.star, v.reviewCnt) from AlcoholVO as a, BeerVO as b, ViewSearchVO as v where a.aid = b.aid and a.aid = v.aid and a.aid = ?1")
	public BeerDTO findByAidBeer(int aid);
	
	// 와인 조회
	@Query(value = "select new com.doubleslash.fifth.dto.WineDTO(a.aid, a.name, a.category, a.image, a.lowestPrice, a.highestPrice, a.ml, a.abv, a.description, a.kind, w.country, w.area, w.flavor, w.body, v.star, v.reviewCnt) from AlcoholVO as a, WineVO as w,ViewSearchVO as v where a.aid = w.aid and a.aid = v.aid and a.aid = ?1")
	public WineDTO findByAidWine(int aid);
	
	// 유사 주류 조회
	@Query(value = "select new com.doubleslash.fifth.dto.SimilarAlcoholDTO(a.aid, a.image, a.name) from AlcoholVO as a, SimilarAlcoholVO as s where a.aid=s.said and s.aid=?1")
	public List<SimilarAlcoholDTO> findSimilar(int aid);

}
