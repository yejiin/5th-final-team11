package com.doubleslas.fifith.alcohol.model.network.dto.recommend

import com.google.gson.annotations.SerializedName

data class RecommendInfoBeer(
    @SerializedName("kind") val type: RecommendInfoBeerType,
    @SerializedName("area") val place: List<String>
)