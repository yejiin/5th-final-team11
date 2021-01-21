package com.doubleslash.fifth.vo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "ReviewLove")
@Data
public class ReviewLoveVO {

	@Id
	private int id;
	
	private int rid;
	
}
