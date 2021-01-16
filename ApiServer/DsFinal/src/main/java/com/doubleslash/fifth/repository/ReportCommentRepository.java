package com.doubleslash.fifth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doubleslash.fifth.vo.ReportCommentVO;

@Repository
public interface ReportCommentRepository extends JpaRepository<ReportCommentVO, Integer> {

}
