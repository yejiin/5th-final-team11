package com.doubleslas.fifith.alcohol.dto.recommend

import com.google.gson.annotations.SerializedName

data class RecommendInfoData(
    val minAbv: Int,
    val maxAbv: Int,
    val minPrice: Int,
    val maxPrice: Int,
    @SerializedName("cb") val carbonated: String,
    val alcohol: RecommendInfoAlcoholContainer
)