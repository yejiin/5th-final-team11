package com.doubleslash.fifth.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "Liquor")
@ToString(exclude = "alcohol")
@Data
public class LiquorVO {
	
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "aid")
	private int aid;
	
	private String flavor;

	//일대일 조인
	//@OneToOne(fetch = FetchType.LAZY, optional = false)
	//@JoinColumn(name = "aid")
	//@JsonManagedReference
	//private AlcoholVO alcohol;

}
