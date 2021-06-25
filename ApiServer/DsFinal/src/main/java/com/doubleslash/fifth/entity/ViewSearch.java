package com.doubleslash.fifth.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ViewSearch")
@Getter
@Setter
public class ViewSearch {
	
	@Id
	private int aid;
	
	private String name;
	
	private String category;
	
	private String thumbnail;
	
	private int price;
	
	private double abv;
	
	private double star;
	
	private int loveCnt;
	
	private int reviewCnt;
	
	private double popularScore;
	
}