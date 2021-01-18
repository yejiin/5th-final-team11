package com.doubleslas.fifith.alcohol.model.network.dto

data class WriteReviewData(
    val content: String,
    val star: Int,
    val detail: ReviewDetailData?
)