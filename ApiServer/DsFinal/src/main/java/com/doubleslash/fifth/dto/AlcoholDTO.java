package com.doubleslash.fifth.dto;

import lombok.Data;

@Data
public class AlcoholDTO {

	protected Long aid;
	
	protected String name;
	
	protected String category;
	
	protected String image;
	
	protected int lowestPrice;
	
	protected int highestPrice;
	
	protected int ml;
	
	protected double abv;
	
	protected String description;
	
	protected String kind;
	
	protected double starAvg;
	
	protected int starCnt;
	
	protected boolean loveClick;
    
	protected String userDrink;
    
	
}
