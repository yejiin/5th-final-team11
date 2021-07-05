package com.doubleslash.fifth.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewResponse<T> {

	private List<T> reviewList;
	private Long totalCnt;
}
