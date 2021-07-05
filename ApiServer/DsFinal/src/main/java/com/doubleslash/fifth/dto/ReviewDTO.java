package com.doubleslash.fifth.dto;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.doubleslash.fifth.entity.Comment;
import com.doubleslash.fifth.entity.review.Review;
import com.doubleslash.fifth.entity.review.ReviewDetail;
import com.doubleslash.fifth.entity.review.ReviewLove;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewDTO {

	private Long rid;

	private String nickname;
	
	private String content;
	
	private int love;
	
	private boolean loveClick;
	
	private float star;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy.MM.dd")
	private LocalDateTime reviewDate;

	private ReviewDetailDTO detail;
	
	private List<CommentDTO> comments;
	
	private int commentTotalCnt;

	public ReviewDTO(ReviewDetail review, Long userId) {
        this.rid = review.getId();
        this.nickname = review.getUser().getNickname();
        this.content = review.getContent();
        this.star = review.getStar();
        this.reviewDate = review.getCreatedDate();
        this.commentTotalCnt = review.getComments().size();

        Set<ReviewLove> reviewLoves = review.getReviewLoves();
        this.love = reviewLoves.size();
        reviewLoves.stream().forEach(rl -> {
            if (rl.getUser().getId() == userId)
                this.loveClick = true;
        });

        if (review.getDate() != null)
            this.detail = new ReviewDetailDTO(review);

        this.comments = review.getComments().stream().sorted(Comparator.comparing(Comment::getId).reversed())
                .map(c -> new CommentDTO(c))
                .limit(3)
                .collect(Collectors.toList());
    }
}
