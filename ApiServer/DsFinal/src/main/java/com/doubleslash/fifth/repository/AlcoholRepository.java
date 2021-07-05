package com.doubleslash.fifth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;

import com.doubleslash.fifth.dto.SimilarAlcoholDTO;
import com.doubleslash.fifth.entity.alcohol.Alcohol;
import com.doubleslash.fifth.storage.BeerStorage;
import com.doubleslash.fifth.storage.LiquorStorage;
import com.doubleslash.fifth.storage.WineStorage;

@Repository
public interface AlcoholRepository extends JpaRepository<Alcohol, Long>{
	
	//양주 공통 + 세부 속성 조회
	@Query(value = "select new com.doubleslash.fifth.storage.LiquorStorage(l.id, l.lowestPrice, l.highestPrice, l.abv, l.kind, l.flavor, l.cb, l.recognition) from Liquor as l")
	public List<LiquorStorage> AlcoholJoinLiquor();
	
	//와인 공통 + 세부 속성 조회
	@Query(value = "select new com.doubleslash.fifth.storage.WineStorage(w.id, w.lowestPrice, w.highestPrice, w.abv, w.kind, w.flavor, w.body, w.cb, w.recognition) from Wine as w")
	public List<WineStorage> AlcoholJoinWine();
	
	//맥주 공통 + 세부 속성 조회
	@Query(value = "select new com.doubleslash.fifth.storage.BeerStorage(b.id, b.lowestPrice, b.highestPrice, b.abv, b.kind, b.subKind, b.flavor, b.cb, b.recognition) from Beer as b")
	public List<BeerStorage> AlcoholJoinBeer();

	// 유사 주류 조회
	@Query(value = "select new com.doubleslash.fifth.dto.SimilarAlcoholDTO(a.id, a.thumbnail, a.name) from Alcohol as a, SimilarAlcohol as s where a.id=s.alcohol and s.alcohol=?1")
	public List<SimilarAlcoholDTO> findSimilar(Long aid);

}
