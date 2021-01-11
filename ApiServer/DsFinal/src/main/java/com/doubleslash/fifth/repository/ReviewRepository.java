package com.doubleslash.fifth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.doubleslash.fifth.vo.ReviewVO;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewVO, Integer> {

	@Query(value = "select rid from Review where aid = ?1 order by rid desc limit 1", nativeQuery = true)
	public String findByAid(int aid);
}
