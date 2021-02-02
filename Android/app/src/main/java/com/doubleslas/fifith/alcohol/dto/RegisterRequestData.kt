package com.doubleslas.fifith.alcohol.dto

data class RegisterRequestData(
    val nickname: String,
    val drink: Float,
    val hangover: Float
)