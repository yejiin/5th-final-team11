package com.doubleslash.fifth.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;

@Entity
@Getter
public class AlcoholLove extends BaseEntity {

	@Id
	private int id;
	
	private int aid;
	
}
