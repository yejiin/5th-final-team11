package com.doubleslash.fifth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doubleslash.fifth.entity.SimilarAlcohol;

@Repository
public interface SimilarAlcoholRepository extends JpaRepository<SimilarAlcohol, Integer>{
	

}
