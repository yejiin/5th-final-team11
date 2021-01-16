package com.doubleslash.fifth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.doubleslash.fifth.dto.ReviewDTO;
import com.doubleslash.fifth.vo.ReviewVO;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewVO, Integer> {

	// 리뷰 리스트 조회
	@Query(value = "select new com.doubleslash.fifth.dto.ReviewDTO(r.rid, u.nickname, r.content, r.love, r.create_time) from UserVO as u, ReviewVO as r where r.id = u.id and r.aid=?1")
	public List<ReviewDTO> findByAid(int aid);
	
	// 리뷰 신고 수 증가
	@Transactional
	@Modifying
	@Query(value = "update Review set report=report+1 where rid=?1", nativeQuery = true)
	public void updateReport(int rid);
	
}
