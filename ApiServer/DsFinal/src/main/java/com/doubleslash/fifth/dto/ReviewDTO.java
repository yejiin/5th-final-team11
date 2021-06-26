package com.doubleslash.fifth.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
public class ReviewDTO {

	private Long rid;

	private String nickname;
	
	private String content;
	
	private int love;
	
	private boolean loveClick;
	
	private double star;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy.MM.dd")
	private LocalDateTime reviewDate;

	private DetailReviewDTO detail;
	
	private List<CommentDTO> comments;
	
	private long commentTotalCnt;

	public ReviewDTO(Long rid, String nickname, String content, int love, double star, LocalDateTime reviewDate) {
		this.rid = rid;
		this.nickname = nickname;
		this.content = content;
		this.love = love;
		this.star = star;
		this.reviewDate = reviewDate;
	}
	
	
	
}
