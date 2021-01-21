package com.doubleslash.fifth.vo;

import java.util.Date;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "DetailReview")
@ToString(exclude = "review")
@Data
public class DetailReviewVO {

	@Id
	private int rid;
	
	@Nullable
	private Date date;
	
	@Nullable
	private String place;
	
	@Nullable
	private double drink;
	
	@Nullable
	private int hangover;
	
	@Nullable
	private int price;
	
	@Nullable
	private boolean privacy;
	
}
