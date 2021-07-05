package com.doubleslash.fifth.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Recommend extends BaseEntity {
	
	@Id
	private Long id;
	
	private Long aid;
	
	private String processed;
	
	private float recScore;

}
