package com.doubleslash.fifth.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.doubleslash.fifth.entity.Recommend;

@Repository
public interface RecommendRepository extends JpaRepository<Recommend, Integer>{
	
	@Modifying
	@Transactional
	@Query(value = "insert into Recommend(id, aid, processed, recScore) values(?1, ?2, ?3, ?4)", nativeQuery = true)
	public void insert(Long id, Long aid, String processed, Long recScore);
	
	@Modifying
	@Transactional
	@Query(value = "delete from Recommend where id = ?1", nativeQuery = true)
	public void delete(Long id);
	
	@Query(value = "select * from Recommend where id = ?1 limit 1", nativeQuery = true)
	public Optional<Recommend> findById(Long id);
	
}
