package com.doubleslas.fifith.alcohol.model.network.dto

import com.google.gson.annotations.SerializedName

data class AlcoholSimpleData(
    val aid: Int,
    val name: String,
    val category: String,
    val star: Float,
    val reviewCnt: Int
)