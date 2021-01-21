package com.doubleslash.fifth.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingDTO {
	
	private List<Request> ratingData;
	
	@Getter
	@Setter
	public static class Request {
		
		private int aid;
		
		private double star;
		
	}
	
	@Getter
	@Setter
	@AllArgsConstructor
	public static class Response {
		
		private int aid;
		
		private String image;
		
		private String name;
		
		private String category;
		
		private double star;
		
	}
	
}
