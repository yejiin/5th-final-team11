package com.doubleslash.fifth.storage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BeerStorage {

	int aid;
	
	int lowestPrice;
	
	int highestPrice;
	
	double abv;
	
	String kind;
	
	String subKind;
	
	String flavor;
	
	int cb;
	
	int recognition;
	
}
