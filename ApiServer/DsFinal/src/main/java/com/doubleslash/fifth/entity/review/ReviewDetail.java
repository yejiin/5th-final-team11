package com.doubleslash.fifth.entity.review;

import java.time.LocalDate;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class ReviewDetail extends Review {

	private LocalDate date;
	
	private String place;
	
	private float drink;
	
	private int hangover;
	
	private int price;
	
	private boolean privacy;
}
