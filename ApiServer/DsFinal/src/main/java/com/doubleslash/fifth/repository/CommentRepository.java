package com.doubleslash.fifth.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.doubleslash.fifth.dto.CommentDTO;
import com.doubleslash.fifth.vo.CommentVO;

@Repository
public interface CommentRepository extends JpaRepository<CommentVO, Integer> {
	
	// 댓글 리스트 조회
	@Query(value = "select new com.doubleslash.fifth.dto.CommentDTO(c.cid, u.nickname, c.content, c.create_time) from UserVO as u,CommentVO as c where c.id=u.id and c.rid=?1 order by c.cid")
	public Page<CommentDTO> findByRid(int rid, Pageable pageable);
	
	// 댓글 신고 수 증가
	@Transactional
	@Modifying
	@Query(value = "update Comment set report=report+1 where cid=?1", nativeQuery = true)
	public void updateReport(int cid);
}
