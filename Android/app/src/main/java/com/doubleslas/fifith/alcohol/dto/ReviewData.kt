package com.doubleslas.fifith.alcohol.dto

data class ReviewData(
    val rid: Int,
    val nickname: String,
    val content: String,
    val love: Int,
    val loveClick: Boolean,
    val star: Float,
    val reviewDate: String,
    val detail: ReviewDetailData,
    val comments: List<ReviewCommentData>,
    var isLove: Boolean
)