package com.doubleslas.fifith.alcohol.model.network.dto

data class ReviewList(
    val reviewList: List<ReviewData>,
    val totalCnt: Int
)