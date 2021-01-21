package com.doubleslash.fifth.vo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Recommend")
@AllArgsConstructor
@NoArgsConstructor
public class RecommendVO {
	
	@Id
	private int id;
	
	private int aid;
	
	private double recScore;

}
