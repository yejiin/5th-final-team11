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

@Entity
@Table(name = "Comment")
@Data
public class CommentVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cid;
	
	private int id;
	
	private int rid;
	
	private String content;
	
	private int report;
	
	@CreationTimestamp
	@Temporal(TemporalType.DATE)
	@Column(name = "create_time")
	private Date create_time;
	
}
