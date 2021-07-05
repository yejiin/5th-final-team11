package com.doubleslash.fifth.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.doubleslash.fifth.dto.CabinetDTO;
import com.doubleslash.fifth.entity.User;
import com.doubleslash.fifth.entity.alcohol.Alcohol;
import com.doubleslash.fifth.entity.review.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
	
	@Query(value = "select count(r) from Review r where r.alcohol.id=?1")
	public Long findCountByAid(Long aid);
	
	@Query(value = "select r from Review r where r.user=?1 and r.alcohol=?2 and r.createdDate between ?3 and ?4")
	public Optional<Review> findByUserAndAlcoholAndCreatedDate(User user, Alcohol alcohol, LocalDateTime start, LocalDateTime end);

	// 마신 술 조회
	@Query(value = "select distinct new com.doubleslash.fifth.dto.CabinetDTO(a.id, a.image) from Review r join r.user u join r.alcohol a where u.id = ?1")
	public Page<CabinetDTO> findByUserIdOrderBy(Long id, Pageable pageable);

}
