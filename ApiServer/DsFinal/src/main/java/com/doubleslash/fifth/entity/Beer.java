package com.doubleslash.fifth.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;

@Entity
@Getter
@DiscriminatorValue("세계맥주")
public class Beer extends Alcohol {

	private String subKind;
	
	private String flavor;
}
