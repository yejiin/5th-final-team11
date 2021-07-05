package com.doubleslash.fifth.dto;

import com.doubleslash.fifth.entity.review.ReviewDetail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MyReviewDTO {
	
	private Long rid;
	
	private Long aid;
	
	private String name;

	private float star;

	private String thumbnail;

	private String content;
	
	private ReviewDetailDTO detail;

	public MyReviewDTO(ReviewDetail review) {
		this.rid = review.getId();
		this.aid = review.getAlcohol().getId();
		this.name = review.getAlcohol().getName();
		this.star = review.getStar();
		this.thumbnail = review.getAlcohol().getThumbnail();
		this.content = review.getContent();
		
        if (review.getDate() != null)
            this.detail = new ReviewDetailDTO(review);
	}
	
}
