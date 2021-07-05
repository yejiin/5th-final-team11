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
import com.doubleslash.fifth.entity.User;

import lombok.Getter;

@Entity
@Getter
public class AlcoholLove extends BaseEntity {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "alcoholLove_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "alcohol_id")
	private Alcohol alcohol;
	
    public void addLoveAlcohol(User user, Alcohol alcohol) {
        this.user = user;
        this.alcohol = alcohol;
        alcohol.getAlcoholLoves().add(this);
//        user.getAlcoholLoves().add(this);
    }
}
