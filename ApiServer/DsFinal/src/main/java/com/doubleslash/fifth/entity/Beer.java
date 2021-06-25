package com.doubleslash.fifth.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;

@Entity
@Getter
public class Beer extends BaseEntity {

	@Id
	private int aid;
	
	private String subKind;
	
	private String flavor;
}
