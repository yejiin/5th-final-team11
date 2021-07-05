package com.doubleslash.fifth.dto;

import java.time.LocalDateTime;

import com.doubleslash.fifth.entity.Comment;
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
	
	private Long id;
	
	private String nickname;
	
	private String content;
	
	@JsonFormat(pattern="yyyy.MM.dd", timezone = "Asia/Seoul")
	private LocalDateTime date;
	
    public CommentDTO (Comment comment) {
        this.id = comment.getId();
        this.nickname = comment.getUser().getNickname();
        this.content = comment.getContent();
        this.date = comment.getCreatedDate();
    }
}
