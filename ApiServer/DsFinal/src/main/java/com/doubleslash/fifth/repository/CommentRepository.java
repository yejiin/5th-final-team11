package com.doubleslash.fifth.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.doubleslash.fifth.dto.CommentDTO;
import com.doubleslash.fifth.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	// 댓글 리스트 조회
	@Query(value = "select new com.doubleslash.fifth.dto.CommentDTO(c.id, u.nickname, c.content, c.createdDate) from Comment as c, User as u where c.id=u.id and c.review=?1")
	public Page<CommentDTO> findByRid(Long rid, Pageable pageable);
	
	@Query(value = "select new com.doubleslash.fifth.dto.CommentDTO(c.review.id, u.nickname, c.content, c.createdDate) from Comment as c, User as u where c.id=u.id and c.id = ?1")
	public CommentDTO findByCid(Long cid);
}
