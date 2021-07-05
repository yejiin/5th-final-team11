package com.doubleslash.fifth.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.doubleslash.fifth.dto.CabinetDTO;
import com.doubleslash.fifth.dto.MyReviewDTO;
import com.doubleslash.fifth.entity.User;
import com.doubleslash.fifth.entity.alcohol.Alcohol;
import com.doubleslash.fifth.entity.review.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
	
	@Query(value = "select count(r) from Review r where r.alcohol.id=?1")
	public int findCountByAid(Long aid);
	
	@Query(value = "select r from Review r where r.user=?1 and r.alcohol=?2 and r.createdDate between ?3 and ?4")
	public Optional<Review> findByUserAndAlcoholAndCreatedDate(User user, Alcohol alcohol, LocalDateTime start, LocalDateTime end);

	// 내 기록 조회(최신순)
	@Query(value = "select new com.doubleslash.fifth.dto.MyReviewDTO(r.id, a.id, a.name, r.star, a.thumbnail, r.content) from Review r, Alcohol a where r.alcohol = a.id and r.user = ?1")
	public Page<MyReviewDTO> getMyReviewListOrderByLatest(Long id, Pageable pageable);
	
	// 내 기록 조회(도수순)
	@Query(value = "select new com.doubleslash.fifth.dto.MyReviewDTO(r.id, a.id, a.name, r.star, a.thumbnail, r.content) from Review r, Alcohol a where r.alcohol = a.id and r.user = ?1 order by a.abv desc")
	public Page<MyReviewDTO> getMyReviewListOrderByAbv(Long id, Pageable pageable);
	
	// 마신 술 조회 (중복 제거, 시간순)
	@Query(value = "select distinct new com.doubleslash.fifth.dto.CabinetDTO(r.alcohol.id, a.image) from Review as r, Alcohol as a where r.alcohol = a.id and r.user = ?1")
	public Page<CabinetDTO> findDrinkAlcoholOrderTime(Long id, Pageable pageable);
	
	// 마신 술 조회 (중복 제거, 도수순)
	@Query(value = "select distinct new com.doubleslash.fifth.dto.CabinetDTO(r.alcohol.id, a.image) from Alcohol as a, Review as r where r.alcohol = a.id and r.user = ?1")
	public Page<CabinetDTO> findDrinkAlcoholOrderAbv(Long id, Pageable pageable);

}
