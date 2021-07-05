package com.doubleslash.fifth.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewWriteDTO {
	
	private Float star;

	private String content;
 
	private DetailReviewWriteDTO detail;
}
