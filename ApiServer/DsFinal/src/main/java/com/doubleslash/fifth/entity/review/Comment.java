package com.doubleslash.fifth.entity.review;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.doubleslash.fifth.entity.BaseEntity;
import com.doubleslash.fifth.entity.User;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Comment extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "review_id")
	private Review review;
	
	private String content;
	
    @OneToMany(mappedBy = "comment", orphanRemoval = true)
    private List<CommentReport> commentReports = new ArrayList<>();

//	
	private int report;

	public void addReport() {
		this.report += 1;
	}
	//
	
	public void addComment(User user, Review review, String content) {
		this.user = user;
		this.review = review;
		this.content = content;
		review.getComments().add(this);
	}
}
