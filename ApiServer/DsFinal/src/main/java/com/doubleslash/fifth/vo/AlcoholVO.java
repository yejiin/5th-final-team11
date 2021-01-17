package com.doubleslash.fifth.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "Alcohol")
@Data
public class AlcoholVO {
	
	@Id
	@Column(name = "aid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int aid;
	
	private String name;
	
	private String category;
	
	private String image;
	
	private int lowestPrice;
	
	private int highestPrice;
	
	private int ml;
	
	private double abv;
	
	private String description;
	
	private String kind;
	
	//일대일 조인
	//@OneToOne(fetch = FetchType.LAZY, mappedBy = "alcohol", cascade = CascadeType.ALL)
	//@JsonBackReference
	//private LiquorVO liquor;

}
