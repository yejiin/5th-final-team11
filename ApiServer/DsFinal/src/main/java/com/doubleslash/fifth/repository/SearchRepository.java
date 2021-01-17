package com.doubleslash.fifth.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
	
}