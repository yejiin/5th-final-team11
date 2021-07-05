package com.doubleslash.fifth.dto.response;

import java.util.List;

import com.doubleslash.fifth.dto.CommentDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentResponse {
	
	private List<CommentDTO> commentList;
	private Long totalCnt;
}
