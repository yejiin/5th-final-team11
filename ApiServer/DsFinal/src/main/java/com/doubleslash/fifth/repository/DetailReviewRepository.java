package com.doubleslash.fifth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.doubleslash.fifth.dto.DetailReviewDTO;
import com.doubleslash.fifth.entity.DetailReview;


@Repository
public interface DetailReviewRepository extends JpaRepository<DetailReview, Integer> {
	
	@Query(value = "select new com.doubleslash.fifth.dto.DetailReviewDTO(d.date, d.place, d.drink, d.hangover, d.price) from Review as r, DetailReview as d where r.id=d.rid and d.privacy=0 and r.id=?1")
	public DetailReviewDTO findByRid(Long rid);

}

