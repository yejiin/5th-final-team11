package com.doubleslas.fifith.alcohol.dto

import com.google.gson.annotations.SerializedName

data class DetailData(
    val image: String,

    // 주류 공통속성
    @SerializedName("highestPrice") val highestPrice: Int,
    @SerializedName("lowestPrice") val lowestPrice: Int,
    @SerializedName("ml") val ml: Int,
    @SerializedName("abv") val abv: Float,
    @SerializedName("starAvg") val starAvg: Float,
    @SerializedName("name") val name: String,
    @SerializedName("kind") val kind: List<String>,
    @SerializedName("description") val description: String,
    val similar: List<SimilarAlcoholData>,

    // 와인 추가속성
    @SerializedName("country") val country: String?,
    @SerializedName("area") val area: String?,
    @SerializedName("wineKind") val wineKind: String?,
    @SerializedName("flavor") val flavor: Int?,
    @SerializedName("body") val body: Int?,

    // 양주 추가속성
    @SerializedName("flavors") val flavors: List<String>?,

    // 맥주 추가속성
    @SerializedName("areas") val areas: List<String>?


)