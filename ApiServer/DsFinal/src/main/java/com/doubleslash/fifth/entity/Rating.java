package com.doubleslash.fifth.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Rating extends BaseEntity {

	@Id
	private int id;
	
	private int aid;
	
	private double star;
	
}
