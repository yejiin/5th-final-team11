package com.doubleslash.fifth.vo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "Wine")
@Data
public class WineVO {

	@Id
	private int aid;
	
	private String country;
	
	private String area;
	
	private String town;
	
	private String wineKind;
	
	private int flavor;
	
	private int body;
	
}
