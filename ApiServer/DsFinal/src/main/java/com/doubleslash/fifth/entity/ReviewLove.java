package com.doubleslash.fifth.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "ReviewLove")
@Data
public class ReviewLove extends BaseEntity {

	@Id
	private int id;
	
	private int rid;
	
}
