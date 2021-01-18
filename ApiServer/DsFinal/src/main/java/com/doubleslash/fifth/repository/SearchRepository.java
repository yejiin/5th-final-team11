package com.doubleslash.fifth.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.doubleslash.fifth.dto.AlcoholSearchDTO;
import com.doubleslash.fifth.mapping.SearchMapping;
import com.doubleslash.fifth.vo.ViewSearchVO;

@Repository
public interface SearchRepository extends JpaRepository<ViewSearchVO, Integer>{
	
	//전체 주류 조회
	public Page<SearchMapping> findByAidIsNotNull(Pageable pageable);
	
	//카테고리별 주류 조회
	public Page<SearchMapping> findByCategory(String category, Pageable pageable);
	
	//주류명으로 검색해서 조회
	public Page<SearchMapping> findByNameContaining(String name, Pageable pageable);
	
	@Query(value = "select new com.doubleslash.fifth.dto.AlcoholSearchDTO(v.aid, v.name, v.category, v.star, v.reviewCnt) from ViewSearchVO as v, RecommendVO as r where v.aid = r.aid and r.id = ?1 order by recScore")
	public List<AlcoholSearchDTO> getRecommendDefaultAll(int id); 
	
	@Query(value = "select new com.doubleslash.fifth.dto.AlcoholSearchDTO(v.aid, v.name, v.category, v.star, v.reviewCnt) from ViewSearchVO as v, RecommendVO as r where v.aid = r.aid and r.id = ?1 and category = ?2 order by recScore")
	public List<AlcoholSearchDTO> getRecommendDefault(int id, String category); 
	
	@Query(value = "select new com.doubleslash.fifth.dto.AlcoholSearchDTO(v.aid, v.name, v.category, v.star, v.reviewCnt) from ViewSearchVO as v, RecommendVO as r where v.aid = r.aid and r.id = ?1")
	public List<AlcoholSearchDTO> getRecommendSortingAll(int id, Sort sort); 
	
	@Query(value = "select new com.doubleslash.fifth.dto.AlcoholSearchDTO(v.aid, v.name, v.category, v.star, v.reviewCnt) from ViewSearchVO as v, RecommendVO as r where v.aid = r.aid and r.id = ?1 and category = ?2")
	public List<AlcoholSearchDTO> getRecommendSorting(int id, String category, Sort sort); 
	
}