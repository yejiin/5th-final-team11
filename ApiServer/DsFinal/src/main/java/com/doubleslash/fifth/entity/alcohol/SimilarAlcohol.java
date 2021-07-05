package com.doubleslash.fifth.entity.alcohol;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.doubleslash.fifth.entity.BaseEntity;

import lombok.Getter;

@Entity
@Getter
public class SimilarAlcohol extends BaseEntity {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "similarAlcohol_id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "alcohol_id")
	private Alcohol alcohol;
	
}
