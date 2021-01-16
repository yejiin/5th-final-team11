package com.doubleslash.fifth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.doubleslash.fifth.vo.ReviewLoveVO;

@Repository
public interface ReviewLoveRepository extends JpaRepository<ReviewLoveVO, Integer>{

	// 찜한 리뷰 확인
	@Query(value = "select id, rid from ReviewLove where id = ?1 and rid = ?2", nativeQuery = true)
	public ReviewLoveVO findByIdRid(int id, int rid);
}
