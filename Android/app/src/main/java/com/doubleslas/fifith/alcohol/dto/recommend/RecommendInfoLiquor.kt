package com.doubleslas.fifith.alcohol.dto.recommend

import com.google.gson.annotations.SerializedName

data class RecommendInfoLiquor(
    @SerializedName("kind") val type: List<String>,
    val flavor: List<String>
)