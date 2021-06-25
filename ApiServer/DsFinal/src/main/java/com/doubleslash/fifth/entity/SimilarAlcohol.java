package com.doubleslash.fifth.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SimilarAlcohol")
public class SimilarAlcohol extends BaseEntity {
	
	@Id
	private int aid;
	
	private int said;

}
