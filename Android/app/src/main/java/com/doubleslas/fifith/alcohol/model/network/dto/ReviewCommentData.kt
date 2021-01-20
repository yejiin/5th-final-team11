package com.doubleslas.fifith.alcohol.model.network.dto

data class ReviewCommentData(
    val cid: Int,
    val nickname: String,
    val content: String,
    val commentData: String
)