package com.doubleslas.fifith.alcohol.model.network.dto.recommend

data class RecommendInfoAlcoholContainer(
    val liquor: RecommendInfoLiquor?,
    val wine: RecommendInfoWine?,
    val beer: RecommendInfoBeer?
)
