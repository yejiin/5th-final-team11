package com.doubleslash.fifth.dto.review;

import java.util.List;

import com.doubleslash.fifth.dto.ReviewDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewResponse {

	private List<ReviewDTO> reviewList;
	private int totalCnt;
}
