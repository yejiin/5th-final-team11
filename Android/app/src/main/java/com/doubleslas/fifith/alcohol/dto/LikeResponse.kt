package com.doubleslas.fifith.alcohol.dto

import com.google.gson.annotations.SerializedName

data class LikeResponse(
    val love: Boolean,
    @SerializedName("loveTotalCnt") val loveCount: Int
)