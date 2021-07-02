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

	Long aid;
	
	int lowestPrice;
	
	int highestPrice;
	
	float abv;
	
	String kind;
	
	String flavor;
	
	int cb;
	
	int recognition;
	
}
