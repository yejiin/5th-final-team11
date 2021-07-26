package com.doubleslas.fifith.alcohol.dto

import com.doubleslas.fifith.alcohol.dto.interfaces.IPageLoadData
import com.google.gson.annotations.SerializedName

data class CupboardList(
    private val totalCnt: Int,
    @SerializedName("alcoholList") private val list: List<CupboardData>
) : IPageLoadData<CupboardData> {
    override fun getTotalCount(): Int {
        return totalCnt
    }

    override fun getList(): List<CupboardData> {
        return list
    }
}