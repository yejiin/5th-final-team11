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
		
		private Long aid;
		
		private float star;
		
	}
	
	@Getter
	@Setter
	@AllArgsConstructor
	public static class Response {
		
		private Long aid;
		
		private String thumbnail;
		
		private String name;
		
		private String category;
		
		private float star;
		
	}
	
}
