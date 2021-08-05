package com.doubleslas.fifith.alcohol.dto

data class ReviewDetailData(
    val date: String?,
    val drink: Double?,
    val hangover: Int?,
    val place: String?,
    val price: Int?,
    val privacy: Boolean = false
) {
    constructor(
        date: String?,
        drink: String?,
        hangover: Int,
        place: String?,
        price: String?,
        privacy: Boolean = false
    ) : this(
        if(date.isNullOrEmpty()) null else date,
        if(drink.isNullOrEmpty()) null else drink.toDouble(),
        hangover,
        if(place.isNullOrEmpty()) null else place,
        if(price.isNullOrEmpty()) null else price.toInt(),
        privacy
    )
}