package com.doubleslas.fifith.alcohol.model.network.dto.recommend

import com.google.gson.annotations.SerializedName

data class RecommendInfoBeerType(
    @SerializedName("mainKind") val mainType: List<String>,
    @SerializedName("subKind") val subType: List<String>
)