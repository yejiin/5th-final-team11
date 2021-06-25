package com.doubleslash.fifth.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Recommend extends BaseEntity {
	
	@Id
	private int id;
	
	private int aid;
	
	private String processed;
	
	private double recScore;

}
