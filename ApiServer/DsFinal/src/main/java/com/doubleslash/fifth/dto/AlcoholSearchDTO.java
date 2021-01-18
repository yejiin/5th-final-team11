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
	
	private int aid;
	
	private String name;
	
	private String category;
	
	private double star;
	
	private int reviewCnt;
	
}
