package com.doubleslash.fifth.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class DetailReview extends BaseEntity {

	@Id
	private Long rid;

	private LocalDate date;
	
	private String place;
	
	private double drink;
	
	private int hangover;
	
	private int price;
	
	private boolean privacy;
	
}
