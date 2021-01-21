package com.doubleslash.fifth.storage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LiquorStorage {

	int aid;
	
	int lowestPrice;
	
	int highestPrice;
	
	double abv;
	
	String kind;
	
	String flavor;
	
	int cb;
	
}
