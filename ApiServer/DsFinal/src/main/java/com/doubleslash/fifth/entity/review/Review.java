package com.doubleslash.fifth.entity.review;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.doubleslash.fifth.entity.BaseEntity;
import com.doubleslash.fifth.entity.Comment;
import com.doubleslash.fifth.entity.User;
import com.doubleslash.fifth.entity.alcohol.Alcohol;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.JOINED)
public class Review extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "review_id")
	private Long id;
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alcohol_id")
    private Alcohol alcohol;
	
	private float star; 
	
	private String content;
	
    @OneToMany(mappedBy = "review", orphanRemoval = true)
    private Set<ReviewLove> reviewLoves = new HashSet<>();

    @OneToMany(mappedBy = "review", orphanRemoval = true)
    private Set<ReviewReport> reviewReports = new HashSet<>();

    @OneToMany(mappedBy = "review", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();
    
    public void addReview(User user, Alcohol alcohol, float star, String content) {
        this.setUser(user);
        this.setAlcohol(alcohol);
        this.setStar(star);
        this.setContent(content);
        this.getAlcohol().getReviews().add(this);
    }
}
