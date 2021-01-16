package com.doubleslash.fifth.vo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "ReportReview")
@Data
public class ReportReviewVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int rno;
	
	private int rid;

	private int id;
	
	private String content;
	
}
