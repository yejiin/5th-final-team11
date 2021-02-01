package com.doubleslash.fifth.vo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MLData")
public class MLDataVO {

	@Id
	private int id;
	
	private int aid;
	
	private double star;
	
}
