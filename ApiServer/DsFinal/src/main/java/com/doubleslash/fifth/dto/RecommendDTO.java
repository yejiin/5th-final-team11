package com.doubleslash.fifth.dto;

import java.util.List;

import lombok.AllArgsConstructor;
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
		List<String> kind;
		List<String> flavor;
	}
	
	@Getter
	@Setter
	public static class wine{
		List<String> kind;
		int flavor;
		int body;
	}
	
	@Getter
	@Setter
	public static class beer{
		beerKind kind;
		List<String> area;
	}
	
	@Getter
	@Setter
	public static class beerKind{
		List<String> mainKind;
		List<String> subKind;
	}
	
	@Getter
	@Setter
	@AllArgsConstructor
	public static class map{
		String category;
		Long recScore;
	}
	
}
