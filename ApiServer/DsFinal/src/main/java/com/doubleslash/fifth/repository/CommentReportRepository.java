package com.doubleslash.fifth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doubleslash.fifth.entity.CommentReport;

@Repository
public interface CommentReportRepository extends JpaRepository<CommentReport, Integer> {

}
