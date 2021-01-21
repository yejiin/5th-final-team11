package com.doubleslash.fifth.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.doubleslash.fifth.vo.RecommendVO;

@Repository
public interface RecommendRepository extends JpaRepository<RecommendVO, Integer>{
	
	@Modifying
	@Transactional
	@Query(value = "insert into Recommend(id, aid, recScore) values(?1, ?2, ?3)", nativeQuery = true)
	public void insert(int id, int aid, int recScore);
	
	@Modifying
	@Transactional
	@Query(value = "delete from Recommend where id = ?1", nativeQuery = true)
	public void delete(int id);
	
	@Query(value = "select * from Recommend where id = ?1 limit 1", nativeQuery = true)
	public Optional<RecommendVO> findById(int id);
	
}
