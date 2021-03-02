package com.doubleslas.fifith.alcohol.dto

import com.doubleslas.fifith.alcohol.dto.interfaces.IPageLoadData

data class ReviewList(
    val reviewList: List<ReviewData>,
    val totalCnt: Int
) : IPageLoadData<ReviewData> {
    override fun getTotalCount(): Int {
        return totalCnt
    }

    override fun getList(): List<ReviewData> {
        return reviewList
    }

}