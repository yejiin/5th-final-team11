package com.doubleslas.fifith.alcohol.model.network.dto.recommend

import com.google.gson.annotations.SerializedName

data class RecommendInfoWine(
    @SerializedName("kind") val type: List<String>,
    val body: Int,
    val flavor: Int
)