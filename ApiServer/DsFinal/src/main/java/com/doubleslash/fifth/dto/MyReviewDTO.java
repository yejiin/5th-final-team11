package com.doubleslash.fifth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MyReviewDTO {
	
	private Long rid;
	
	private Long aid;
	
	private String name;

	private double star;

	private String thumbnail;

	private String content;
	
	private ReviewDetailDTO detail;

	public MyReviewDTO(Long rid, Long aid, String name, double star, String thumbnail, String content) {
		super();
		this.rid = rid;
		this.aid = aid;
		this.name = name;
		this.star = star;
		this.thumbnail = thumbnail;
		this.content = content;
	}
	
}
