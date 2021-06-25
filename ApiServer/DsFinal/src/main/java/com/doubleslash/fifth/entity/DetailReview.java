package com.doubleslash.fifth.entity;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString(exclude = "review")
@Getter @Setter
public class DetailReview extends BaseEntity {

	@Id
	private int rid;

	private Date date;
	
	private String place;
	
	private double drink;
	
	private int hangover;
	
	private int price;
	
	private boolean privacy;
	
}
