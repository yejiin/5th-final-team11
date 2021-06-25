package com.doubleslash.fifth.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.ToString;

@Entity
@ToString(exclude = "alcohol")
@Getter
public class Liquor extends BaseEntity {
	
	@Id
	private int aid;
	
	private String flavor;
}
