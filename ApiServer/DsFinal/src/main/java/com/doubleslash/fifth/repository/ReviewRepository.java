package com.doubleslash.fifth.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	@Query(value = "select new com.doubleslash.fifth.dto.ReviewDTO(r.rid, u.nickname, r.content, r.love, r.star, r.create_time) from ReviewVO as r, UserVO as u where r.id = u.id and r.aid=?1")
	public Page<ReviewDTO> findByAid(int aid, Pageable pageable);
	
	@Query(value = "select rid, aid, id, star, content, love, report, create_time from Review where id=?1 and date(create_time)=?2", nativeQuery = true)
	public ReviewVO findById(int id, String createTime);

	// 리뷰 신고 수 증가
	@Transactional
	@Modifying
	@Query(value = "update Review set report=report+1 where rid=?1", nativeQuery = true)
	public void updateReport(int rid);
	
	// 리뷰 좋아요 수 증가
	@Transactional
	@Modifying
	@Query(value = "update Review set love=love+1 where rid=?1", nativeQuery = true)
	public void updateLove(int rid);
	
	// 리뷰 좋아요 수 증가
	@Transactional
	@Modifying
	@Query(value = "update Review set love=love-1 where rid=?1", nativeQuery = true)
	public void updateLoveCancle(int rid);
	
	@Query(value = "select rid from Review where aid = ?1 order by rid desc limit 1", nativeQuery = true)
	public String findByAid(int aid);

}
