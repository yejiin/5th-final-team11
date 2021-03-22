package com.doubleslas.fifith.alcohol.dto

import com.google.gson.annotations.SerializedName

data class DetailData(
    val aid: Int,
    val name: String,
    val category: String,
    val image: String,
    val lowestPrice: Int,
    val highestPrice: Int,
    val ml: Int,
    val abv: Float,
    val description: String,
    val kind: String,
    val starAvg: Float,
    val similar: List<SimilarAlcoholData>,

    // 주류 공통속성
    val userDrink: String?,

    // 와인 추가속성
    @SerializedName("country") val country: String?,
    @SerializedName("area") val area: String?,
    @SerializedName("flavor") val flavor: Int?,
    @SerializedName("body") val body: Int?,

    val flavors: List<String>?,
    val subKind: String?


)