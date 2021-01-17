package com.doubleslash.fifth.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecommendDTO {

	double minAbv;
	
	double maxAbv;
	
	int minPrice;
	
	int maxPrice;
	
	String Cb;
	
	alcohol alcohol;
	
	@Getter
	@Setter
	public static class alcohol{
		liquor liquor;
		wine wine;
		beer beer;
	}
	
	@Getter
	@Setter
	public static class liquor{
		String kind;
		String flavor;
	}
	
	@Getter
	@Setter
	public static class wine{
		String kind;
		int flavor;
		int body;
	}
	
	@Getter
	@Setter
	public static class beer{
		List<beerKind> kind;
		String area;
	}
	
	@Getter
	@Setter
	public static class beerKind{
		String mainKind;
		String subKind;
	}
	
}
