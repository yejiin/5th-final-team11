package com.doubleslas.fifith.alcohol.dto

import com.google.gson.annotations.SerializedName

data class ReviewCommentData(
    @SerializedName("id") val cid: Int,
    val nickname: String,
    val content: String,
    val commentDate: String
)