package com.doubleslas.fifith.alcohol.model.network.dto

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class SearchList(
    @SerializedName("searchList") private val list: List<AlcoholSimpleData>,
    @SerializedName("searchCnt") private val totalCount: Int
) : IPageList<AlcoholSimpleData> {
    override fun getList(): List<AlcoholSimpleData> = list
    override fun getTotalCount(): Int = totalCount
}