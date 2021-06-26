package com.doubleslash.fifth.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.doubleslash.fifth.dto.CabinetDTO;
import com.doubleslash.fifth.dto.MyReviewDTO;
import com.doubleslash.fifth.dto.ReviewDTO;
import com.doubleslash.fifth.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

	// 리뷰 리스트 조회
	@Query(value = "select new com.doubleslash.fifth.dto.ReviewDTO(r.id, u.nickname, r.content, r.love, r.star, r.createdDate) from Review as r, User as u where r.user = u.id and r.alcohol=?1 order by field(u.id, ?2) desc")
	public Page<ReviewDTO> findByAid(Long aid, Long id, Pageable pageable);
	
	@Query(value = "select rid, aid, id, star, content, love, report, create_time from Review where id=?1 and aid=?2 and date(create_time)=?3", nativeQuery = true)
	public Review findById(Long id, Long aid, String createTime);
	
	@Query(value = "select rid from Review where aid = ?1 order by rid desc limit 1", nativeQuery = true)
	public String findByAid(Long aid);

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
