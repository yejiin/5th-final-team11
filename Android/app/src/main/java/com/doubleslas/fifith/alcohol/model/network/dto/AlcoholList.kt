package com.doubleslas.fifith.alcohol.model.network.dto

import com.google.gson.annotations.SerializedName

data class AlcoholList(
    @SerializedName("response") val list: List<AlcoholSimpleData>
)