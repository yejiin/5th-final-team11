package com.doubleslash.fifth.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewWriteDTO {
	
	private float star;

	private String content;
 
	private DetailReviewWriteDTO detail;
}
