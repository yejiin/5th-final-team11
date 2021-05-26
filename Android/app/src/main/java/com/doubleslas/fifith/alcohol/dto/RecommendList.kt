package com.doubleslas.fifith.alcohol.dto

import com.doubleslas.fifith.alcohol.dto.interfaces.IPageLoadData
import com.google.gson.annotations.SerializedName

data class RecommendList(
    @SerializedName("recommendList") private val list: List<AlcoholSimpleData>,
    @SerializedName("totalCnt") private val totalCnt: Int
) : IPageLoadData<AlcoholSimpleData> {
    override fun getTotalCount(): Int {
        return totalCnt
    }

    override fun getList(): List<AlcoholSimpleData> {
        return list
    }

}