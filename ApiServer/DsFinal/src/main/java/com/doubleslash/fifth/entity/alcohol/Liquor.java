package com.doubleslash.fifth.entity.alcohol;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Getter;

@Entity
@Getter
@DiscriminatorValue("양주")
public class Liquor extends Alcohol {
	
	private String flavor;
	private String subFlavor;
}
