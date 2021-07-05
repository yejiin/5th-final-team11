package com.doubleslash.fifth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlcoholSearchDTO {
	
	private Long aid;
	
	private String name;
	
	private String category;
	
	private String thumbnail;
	
	private float star;
	
	private int reviewCnt;
	
}
