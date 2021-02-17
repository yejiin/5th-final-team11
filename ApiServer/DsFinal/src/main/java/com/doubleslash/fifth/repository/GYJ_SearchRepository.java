package com.doubleslash.fifth.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.doubleslash.fifth.dto.AlcoholSearchDTO;
import com.doubleslash.fifth.dto.GYJ_AlcoholSearchDTO;
import com.doubleslash.fifth.vo.ViewSearchVO;

@Repository
public interface GYJ_SearchRepository extends JpaRepository<ViewSearchVO, Integer>{

	/*
	 * GYJ Recommend Test API
	 * */
	@Query(value = "select new com.doubleslash.fifth.dto.GYJ_AlcoholSearchDTO(v.name, v.category, r.recScore) from ViewSearchVO as v, RecommendVO as r where v.aid = r.aid and r.id = ?1 order by recScore desc")
	public List<GYJ_AlcoholSearchDTO> GYJ_getRecommendDefaultAll(int id); 
	
	@Query(value = "select new com.doubleslash.fifth.dto.GYJ_AlcoholSearchDTO(v.name, v.category, r.recScore) from ViewSearchVO as v, RecommendVO as r where v.aid = r.aid and r.id = ?1 and category = ?2 order by recScore desc")
	public List<GYJ_AlcoholSearchDTO> GYJ_getRecommendDefault(int id, String category); 
	
	@Query(value = "select new com.doubleslash.fifth.dto.GYJ_AlcoholSearchDTO(v.name, v.category, r.recScore) from ViewSearchVO as v, RecommendVO as r where v.aid = r.aid and r.id = ?1")
	public List<GYJ_AlcoholSearchDTO> GYJ_getRecommendSortingAll(int id, Sort sort); 
	
	@Query(value = "select new com.doubleslash.fifth.dto.GYJ_AlcoholSearchDTO(v.name, v.category, r.recScore) from ViewSearchVO as v, RecommendVO as r where v.aid = r.aid and r.id = ?1 and category = ?2")
	public List<GYJ_AlcoholSearchDTO> GYJ_getRecommendSorting(int id, String category, Sort sort);
	
}
