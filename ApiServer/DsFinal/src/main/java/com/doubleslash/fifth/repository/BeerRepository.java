package com.doubleslash.fifth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doubleslash.fifth.vo.BeerVO;

@Repository
public interface BeerRepository extends JpaRepository<BeerVO, Integer> {

}
