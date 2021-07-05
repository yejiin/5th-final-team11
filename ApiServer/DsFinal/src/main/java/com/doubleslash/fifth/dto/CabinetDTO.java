package com.doubleslash.fifth.dto;

import com.doubleslash.fifth.entity.review.Review;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CabinetDTO {
	
	private Long aid;
	
	private String image;

	public CabinetDTO(Review r) {
		this.aid = r.getAlcohol().getId();
		this.image = r.getAlcohol().getImage();
	}
}
