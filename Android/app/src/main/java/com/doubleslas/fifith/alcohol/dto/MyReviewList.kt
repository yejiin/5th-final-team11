package com.doubleslas.fifith.alcohol.dto

import com.doubleslas.fifith.alcohol.dto.interfaces.IPageLoadData
import com.google.gson.annotations.SerializedName

data class MyReviewList(
    @SerializedName("totalCnt") private val totalCount: Int,
    @SerializedName("reviewList") private val list: List<MyReviewData>
) : IPageLoadData<MyReviewData> {
    override fun getTotalCount(): Int {
        return totalCount
    }

    override fun getList(): List<MyReviewData> {
        return list
    }
}