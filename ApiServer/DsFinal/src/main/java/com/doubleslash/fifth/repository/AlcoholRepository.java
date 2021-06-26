package com.doubleslash.fifth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;

import com.doubleslash.fifth.dto.BeerDTO;
import com.doubleslash.fifth.dto.LiquorDTO;
import com.doubleslash.fifth.dto.SimilarAlcoholDTO;
import com.doubleslash.fifth.dto.WineDTO;
import com.doubleslash.fifth.entity.Alcohol;
import com.doubleslash.fifth.storage.BeerStorage;
import com.doubleslash.fifth.storage.LiquorStorage;
import com.doubleslash.fifth.storage.WineStorage;

@Repository
public interface AlcoholRepository extends JpaRepository<Alcohol, Long>{
	
	//양주 공통 + 세부 속성 조회
	@Query(value = "select new com.doubleslash.fifth.storage.LiquorStorage(a.id, a.lowestPrice, a.highestPrice, a.abv, a.kind, l.flavor, a.cb, a.recognition) from Alcohol as a, Liquor as l where a.id = l.aid")
	public List<LiquorStorage> AlcoholJoinLiquor();
	
	//와인 공통 + 세부 속성 조회
	@Query(value = "select new com.doubleslash.fifth.storage.WineStorage(a.id, a.lowestPrice, a.highestPrice, a.abv, a.kind, w.flavor, w.body, a.cb, a.recognition) from Alcohol as a, Wine as w where a.id = w.aid")
	public List<WineStorage> AlcoholJoinWine();
	
	//맥주 공통 + 세부 속성 조회
	@Query(value = "select new com.doubleslash.fifth.storage.BeerStorage(a.id, a.lowestPrice, a.highestPrice, a.abv, a.kind, b.subKind, b.flavor, a.cb, a.recognition) from Alcohol as a, Beer as b where a.id = b.aid")
	public List<BeerStorage> AlcoholJoinBeer();
	
	// 양주 조회
	@Query(value = "select new com.doubleslash.fifth.dto.LiquorDTO(a.id, a.name, a.category, a.image, a.lowestPrice, a.highestPrice, a.ml, a.abv, a.description, a.kind, l.flavor, v.star, v.reviewCnt) from Alcohol as a, ViewSearch as v, Liquor as l where a.id = v.aid and a.id = l.aid and a.id = ?1")
	public LiquorDTO findByAidLiquor(Long aid);

	// 맥주 조회
	@Query(value = "select new com.doubleslash.fifth.dto.BeerDTO(a.id, a.name, a.category, a.image, a.lowestPrice, a.highestPrice, a.ml, a.abv, a.description, a.kind, b.subKind, b.flavor, v.star, v.reviewCnt) from Alcohol as a, Beer as b, ViewSearch as v where a.id = b.aid and a.id = v.aid and a.id = ?1")
	public BeerDTO findByAidBeer(Long aid);
	
	// 와인 조회
	@Query(value = "select new com.doubleslash.fifth.dto.WineDTO(a.id, a.name, a.category, a.image, a.lowestPrice, a.highestPrice, a.ml, a.abv, a.description, a.kind, w.country, w.area, w.flavor, w.body, v.star, v.reviewCnt) from Alcohol as a, Wine as w,ViewSearch as v where a.id = w.aid and a.id = v.aid and a.id = ?1")
	public WineDTO findByAidWine(Long aid);
	
	// 유사 주류 조회
	@Query(value = "select new com.doubleslash.fifth.dto.SimilarAlcoholDTO(a.id, a.thumbnail, a.name) from Alcohol as a, SimilarAlcohol as s where a.id=s.alcohol and s.alcohol=?1")
	public List<SimilarAlcoholDTO> findSimilar(Long aid);

}
