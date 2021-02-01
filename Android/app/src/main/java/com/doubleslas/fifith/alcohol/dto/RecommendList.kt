package com.doubleslas.fifith.alcohol.dto

import com.doubleslas.fifith.alcohol.dto.interfaces.IPageLoadData
import com.google.gson.annotations.SerializedName

data class RecommendList(
    @SerializedName("response") private val list: List<AlcoholSimpleData>
): IPageLoadData<AlcoholSimpleData> {
    override fun getTotalCount(): Int {
        return list.size
    }

    override fun getList(): List<AlcoholSimpleData> {
        return list
    }

}