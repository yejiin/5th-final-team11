package com.doubleslash.fifth.vo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ReportComment")
public class ReportCommentVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int rno;
	
	private int cid;
	
	private String content;
	
}
