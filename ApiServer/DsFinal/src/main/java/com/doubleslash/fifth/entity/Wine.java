package com.doubleslash.fifth.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;

@Entity
@Table(name = "Wine")
@Getter 
public class Wine extends BaseEntity {

	@Id
	private int aid;
	
	private String country;
	
	private String area;
	
	private int flavor;
	
	private int body;
	
}
