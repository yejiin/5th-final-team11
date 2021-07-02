package com.doubleslash.fifth.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Getter;

@Entity
@Getter 
@DiscriminatorValue("와인")
public class Wine extends Alcohol {
	
	private String country;
	
	private String area;
	
	private int flavor;
	
	private int body;
	
}
