package com.doubleslash.fifth.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.doubleslash.fifth.vo.ReviewLoveVO;

@Repository
public interface AlcoholLoveRepository extends JpaRepository<ReviewLoveVO, Integer>{

	@Modifying
	@Transactional
	@Query(value = "insert ignore into AlcoholLove(id, aid) values(?1, ?2)", nativeQuery = true)
	public int insert(int id, int aid);
	
	@Modifying
	@Transactional
	@Query(value = "delete from AlcoholLove where id=?1 and aid=?2", nativeQuery = true)
	public int delete(int id, int aid);
}
