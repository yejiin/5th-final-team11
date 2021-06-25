package com.doubleslash.fifth.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
	
	private int id;
	
	private String nickname;
	
	private String content;
	
	@JsonFormat(pattern="yyyy.MM.dd", timezone = "Asia/Seoul")
	private LocalDateTime commentDate;
	
}
