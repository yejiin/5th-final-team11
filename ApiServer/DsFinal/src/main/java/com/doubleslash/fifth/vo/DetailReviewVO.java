package com.doubleslash.fifth.vo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DetailReview")
public class DetailReviewVO {

	@Id
	private int rid;
	
	private Date date;
	
	private String place;
	
	private double drink;
	
	private int hangover;
	
	private int price;
	
	private boolean security;
	
}
