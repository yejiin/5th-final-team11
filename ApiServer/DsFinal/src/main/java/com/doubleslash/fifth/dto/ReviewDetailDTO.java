package com.doubleslash.fifth.dto;

import java.time.LocalDate;

import com.doubleslash.fifth.entity.review.ReviewDetail;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDetailDTO {
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy.MM.dd")
	private LocalDate date;
	
	private String place;
	
	private float drink;
	
	private int hangover;
	
	private int price;
	
    public ReviewDetailDTO(ReviewDetail detail) {
        this.place = detail.getPlace();
        this.drink = detail.getDrink();
        this.hangover = detail.getHangover();
        this.price = detail.getPrice();
        this.date = detail.getDate();
    }
}
