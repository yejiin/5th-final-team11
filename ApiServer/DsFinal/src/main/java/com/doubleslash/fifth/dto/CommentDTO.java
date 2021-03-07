package com.doubleslash.fifth.dto;

import java.util.Date;

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
	private Date commentDate;
	
}
