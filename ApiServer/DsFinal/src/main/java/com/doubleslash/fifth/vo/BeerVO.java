package com.doubleslash.fifth.vo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "Beer")
@Data
public class BeerVO {

	@Id
	private int aid;
	
	private String subKind;
	
	private String area;
}
