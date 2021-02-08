package com.doubleslas.fifith.alcohol.dto

data class AlcoholSimpleData(
    val aid: Int,
    val name: String,
    val category: String,
    val star: Float,
    val reviewCnt: Int,
    val thumbnail: String
)