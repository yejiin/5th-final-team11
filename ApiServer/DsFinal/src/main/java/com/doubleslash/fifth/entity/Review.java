package com.doubleslash.fifth.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Review")
@Getter @Setter
@NoArgsConstructor
public class Review extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int rid;
	
	private int aid;
	
	private int id;
	
	private double star; 
	
	private String content;
	
	private int love;
	
	private int report;
	
	public void addLove() {
		this.love += 1;
	}
	
	public void cancelLove() {
		
		if (this.love < 0) {
			throw new RuntimeException("Error cancel review love");
		}
		this.love -= 1;
	}
	
	public void addReport() {
		this.report += 1;
	}
	
}
