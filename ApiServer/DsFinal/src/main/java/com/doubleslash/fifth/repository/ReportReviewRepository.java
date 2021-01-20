package com.doubleslash.fifth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doubleslash.fifth.vo.ReportReviewVO;

@Repository
public interface ReportReviewRepository extends JpaRepository<ReportReviewVO, Integer> {

}
