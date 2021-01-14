package com.doubleslash.fifth.vo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Review")
public class ReviewVO {

	@Id
	private int rid;
	
	private int aid;
	
	private int id;
	
	private double star;
	
	private String content;
	
	private int love;
	
	private int report;
	
}
