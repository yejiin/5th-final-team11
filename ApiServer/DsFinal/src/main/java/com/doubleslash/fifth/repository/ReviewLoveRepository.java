package com.doubleslash.fifth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doubleslash.fifth.entity.User;
import com.doubleslash.fifth.entity.review.Review;
import com.doubleslash.fifth.entity.review.ReviewLove;

@Repository
public interface ReviewLoveRepository extends JpaRepository<ReviewLove, Integer>{

	public Optional<ReviewLove> findByUserAndReview(User user, Review review);
	
}
