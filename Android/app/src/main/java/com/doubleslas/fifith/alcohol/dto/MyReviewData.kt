package com.doubleslas.fifith.alcohol.dto

data class MyReviewData(
    val alcoholName: String,
    val alcoholType: String,
    val star: Float,
    val review: String,
    val date: String,
    val location: String,
    val price: String
)