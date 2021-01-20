package com.doubleslash.fifth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doubleslash.fifth.vo.SimilarAlcoholVO;

@Repository
public interface SimilarAlcoholRepository extends JpaRepository<SimilarAlcoholVO, Integer>{
	

}
