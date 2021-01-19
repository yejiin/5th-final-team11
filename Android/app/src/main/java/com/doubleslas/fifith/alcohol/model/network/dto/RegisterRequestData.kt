package com.doubleslas.fifith.alcohol.model.network.dto

data class RegisterRequestData(
    val nickname: String,
    val drink: Float,
    val hangover: Float
)