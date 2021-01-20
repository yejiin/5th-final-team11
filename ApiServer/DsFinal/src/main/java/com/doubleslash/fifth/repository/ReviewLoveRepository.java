package com.doubleslash.fifth.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.doubleslash.fifth.vo.ReviewLoveVO;

@Repository
public interface ReviewLoveRepository extends JpaRepository<ReviewLoveVO, Integer>{

	// 찜한 리뷰 확인
	@Query(value = "select id, rid from ReviewLove where id = ?1 and rid = ?2", nativeQuery = true)
	public ReviewLoveVO findByIdRid(int id, int rid);
	
	@Modifying
	@Transactional
	@Query(value = "insert ignore into ReviewLove(id, rid) values(?1, ?2)", nativeQuery = true)
	public int insert(int id, int rid);
	
	@Modifying
	@Transactional
	@Query(value = "delete from ReviewLove where id=?1 and rid=?2", nativeQuery = true)
	public int delete(int id, int rid);
}
