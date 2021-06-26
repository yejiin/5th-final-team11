package com.doubleslash.fifth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;

@Entity
@Getter
public class Alcohol extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "alcohol_id")
	private Long id;
	
	private String name;
	
	private String category;
	
	private String image;
	
	private String thumbnail;
	
	private int lowestPrice;
	
	private int highestPrice;
	
	private int ml;
	
	private double abv;
	
	private String description;
	
	private String kind;
	
	private int cb;
	
	private int recognition;
}
