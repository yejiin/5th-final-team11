package com.doubleslash.fifth.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.doubleslash.fifth.dto.ReviewDetailDTO;
import com.doubleslash.fifth.entity.review.ReviewDetail;


@Repository
public interface ReviewDetailRepository extends JpaRepository<ReviewDetail, Long> {
	
	// 리뷰 리스트 조회
	@Query(value = "select r from ReviewDetail r join fetch r.user u join fetch r.alcohol a where r.alcohol.id=?1 order by field(u.id, ?2) desc")
	public List<ReviewDetail> findByAid(Long aid, Long id, Pageable pageable);

	@Query(value = "select new com.doubleslash.fifth.dto.DetailReviewDTO(d.date, d.place, d.drink, d.hangover, d.price) from Review as r, DetailReview as d where r.id=d.rid and d.privacy=0 and r.id=?1")
	public ReviewDetailDTO findByRid(Long rid);
	
}

