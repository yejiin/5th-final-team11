package com.doubleslash.fifth.vo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "Review")
@Data
public class ReviewVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int rid;
	
	private int aid;
	
	private int id;
	
	private float star; 
	
	// 수정 필요
}
