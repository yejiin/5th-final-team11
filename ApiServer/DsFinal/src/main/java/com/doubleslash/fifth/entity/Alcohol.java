package com.doubleslash.fifth.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

import lombok.Getter;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "category")
public class Alcohol extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "alcohol_id")
	private Long id;
	
	private String name;
	
	private String category;
	
	private String image;
	
	private String thumbnail;
	
	private int lowestPrice;
	
	private int highestPrice;
	
	private int ml;
	
	private float abv;
	
	private String description;
	
	private String kind;
	
	private int cb;
	
	private int recognition;
	
    @OneToMany(mappedBy = "alcohol")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "alcohol")  
    private Set<AlcoholLove> alcoholLoves = new HashSet<>();

}
