package com.doubleslash.fifth.storage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WineStorage {

	Long aid;
	
	int lowestPrice;
	
	int highestPrice;
	
	double abv;
	
	String kind;
	
	int flavor;
	
	int body;
	
	int cb;
	
	int recognition;
	
}
