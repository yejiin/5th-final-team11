package com.doubleslash.fifth.vo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SimilarAlcohol")
public class SimilarAlcoholVO {
	
	@Id
	private int aid;
	
	private int said;

}
