package com.doubleslas.fifith.alcohol.dto

import com.google.gson.annotations.SerializedName

data class MyReviewData(
    val rid: Int,
    val aid: Int,
    @SerializedName("name") val alcoholName: String,
    val star: Float,
    val thumbnail: String,
    val content: String,
    val detail: ReviewDetailData?,
    var visibleReview: Boolean = false
){
    var isSelect = false
}