package com.doubleslash.fifth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LiquorDTO{

	private Long aid;
	
	private String name;
	
	private String category;
	
	private String image;
	
	private int lowestPrice;
	
	private int highestPrice;
	
	private int ml;
	
	private double abv;
	
	private String description;
	
	private String kind;
	
	private String flavors;
	
	private double starAvg;
	
	private int starCnt;

}
