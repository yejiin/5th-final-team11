package com.doubleslash.fifth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doubleslash.fifth.vo.RecommendVO;

@Repository
public interface RecommendRepository extends JpaRepository<RecommendVO, Integer>{
	
}
