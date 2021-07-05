package com.doubleslash.fifth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doubleslash.fifth.entity.review.ReviewReport;

@Repository
public interface ReviewReportRepository extends JpaRepository<ReviewReport, Integer> {

}
