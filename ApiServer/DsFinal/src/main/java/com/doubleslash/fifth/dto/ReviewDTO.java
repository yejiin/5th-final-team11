package com.doubleslash.fifth.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class ReviewDTO {

	@NonNull
	private int rid;
	
	@NonNull
	private String nickname;
	
	@NonNull
	private String content;
	
	@NonNull
	private int love;
	
	private boolean loveClick;
	
	@NonNull
	private double star;

	
	@NonNull
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy.MM.dd")
	private Date reviewDate;

	private DetailReviewDTO detail;
	
	private List<CommentDTO> comments;
	
	private long commentTotalCnt;
	
}
