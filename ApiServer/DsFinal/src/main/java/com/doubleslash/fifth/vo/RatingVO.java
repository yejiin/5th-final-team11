package com.doubleslash.fifth.vo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Rating")
@Getter
@Setter
@AllArgsConstructor
public class RatingVO {

	@Id
	private int id;
	
	private int aid;
	
	private double star;
	
}
