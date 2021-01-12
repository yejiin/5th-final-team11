package com.doubleslas.fifith.alcohol.model.network.dto

import com.google.gson.annotations.SerializedName

data class SearchList(
    @SerializedName("searchList") val list: List<AlcoholSimpleData>,
    @SerializedName("searchCnt") val totalCount: Int
)