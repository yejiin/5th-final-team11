package com.doubleslash.fifth.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Rating extends BaseEntity {

	@Id
	private Long id;
	
	private Long aid;
	
	private float star;
	
}
