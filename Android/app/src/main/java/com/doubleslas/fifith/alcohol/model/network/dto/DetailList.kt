package com.doubleslas.fifith.alcohol.model.network.dto

import com.google.gson.annotations.SerializedName

data class DetailList (
    @SerializedName("highestPrice") val highestPrice: Int,
    @SerializedName("lowestPrice") val lowestPrice: Int,
    @SerializedName("ml") val ml: Int,
    @SerializedName("abv") val abv: Double,
    @SerializedName("starAvg") val starAvg: Float,
    @SerializedName("name") val name: String,

    // 와인 추가속성
    @SerializedName("country") val country: String?,
    @SerializedName("area") val area: String?,
    @SerializedName("town") val town: String?,
    @SerializedName("wineKind") val wineKind: String?

)