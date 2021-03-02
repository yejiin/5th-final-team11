package com.doubleslas.fifith.alcohol.dto

import com.google.gson.annotations.SerializedName

data class SimilarAlcoholData(
    @SerializedName("aImage") val image: String,
    @SerializedName("aName") val name: String,
    @SerializedName("aId") val id: Int
)