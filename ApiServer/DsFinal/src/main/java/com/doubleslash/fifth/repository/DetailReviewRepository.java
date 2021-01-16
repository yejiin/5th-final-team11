package com.doubleslash.fifth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.doubleslash.fifth.dto.DetailReviewDTO;
import com.doubleslash.fifth.vo.DetailReviewVO;


@Repository
public interface DetailReviewRepository extends JpaRepository<DetailReviewVO, Integer> {
	
	@Query(value = "select new com.doubleslash.fifth.dto.DetailReviewDTO(d.date, d.place, d.drink, d.hangover, d.price) from ReviewVO as r, DetailReviewVO as d where r.rid=d.rid and d.security=0 and r.rid=?1")
	public DetailReviewDTO fineByRid(int rid);
}
