package com.doubleslash.fifth.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.doubleslash.fifth.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	// 댓글 리스트 조회
	@Query(value = "select c from Comment c where c.review.id=?1")
	public Page<Comment> findByRid(Long rid, Pageable pageable);
}
