package com.doubleslash.fifth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Users")
@Getter @Setter
@NoArgsConstructor
public class User extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;
	
	@Column(unique = true)
	private String uid;
	
	private String nickname;
	
	private float drink;
	
	private int hangover;

	public User(String uid) {
		super();
		this.uid = uid;
	}
}