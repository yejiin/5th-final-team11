package com.doubleslash.fifth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class MyReviewDTO {
	
	@NonNull
	private int rid;
	@NonNull
	private int aid;
	@NonNull
	private String name;
	@NonNull
	private double star;
	@NonNull
	private String thumbnail;
	@NonNull
	private String content;
	
	private DetailReviewDTO detail;
	
}
