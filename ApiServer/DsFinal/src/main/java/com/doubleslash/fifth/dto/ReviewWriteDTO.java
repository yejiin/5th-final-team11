package com.doubleslash.fifth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class ReviewWriteDTO {
	
	@NonNull
	private double star;

	@NonNull
	private String content;
 
	private DetailReviewWriteDTO detail;
}
