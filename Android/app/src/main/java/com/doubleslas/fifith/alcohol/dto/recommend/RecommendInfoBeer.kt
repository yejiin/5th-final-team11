package com.doubleslas.fifith.alcohol.dto.recommend

import com.google.gson.annotations.SerializedName

data class RecommendInfoBeer(
    @SerializedName("kind") val type: RecommendInfoBeerType,
    @SerializedName("area") val place: List<String>
)