package com.doubleslash.fifth.vo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "AlcoholLove")
@Data
public class AlcoholLoveVO {

	@Id
	private int id;
	
	private int aid;
	
}
