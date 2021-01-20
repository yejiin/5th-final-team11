package com.doubleslash.fifth.vo;

import java.util.Date;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

import lombok.Data;

@Entity
@Table(name = "Review")
@Data
@NoArgsConstructor
public class ReviewVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int rid;
	
	private int aid;
	
	private int id;
	
	private double star; 
	
	private String content;
	
	private int love;
	
	private int report;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private Date create_time;
	
	private float star; 
	
}
