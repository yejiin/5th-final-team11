package com.doubleslas.fifith.alcohol.model.network.dto

data class AlcoholSimpleData(
    val imageUrl: String,
    val name: String,
    val type: String,
    val rating: Float,
    val numVote: Int
)