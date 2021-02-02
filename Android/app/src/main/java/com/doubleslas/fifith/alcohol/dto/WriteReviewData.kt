package com.doubleslas.fifith.alcohol.dto

data class WriteReviewData(
    val content: String,
    val star: Int,
    val detail: ReviewDetailData?
)