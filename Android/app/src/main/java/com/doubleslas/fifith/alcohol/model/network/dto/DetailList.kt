package com.doubleslas.fifith.alcohol.model.network.dto

import com.google.gson.annotations.SerializedName

data class DetailList (
    @SerializedName("highestPrice") val highestPrice: Int,
    @SerializedName("lowestPrice") val lowestPrice: Int,
    @SerializedName("ml") val ml: Int,
    @SerializedName("abv") val abv: Double

)