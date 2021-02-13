package com.doubleslas.fifith.alcohol.ui.record

import com.doubleslas.fifith.alcohol.dto.MyReviewList
import com.doubleslas.fifith.alcohol.enum.MyReviewSortType
import com.doubleslas.fifith.alcohol.model.base.ApiLiveData
import com.doubleslas.fifith.alcohol.model.base.RestClient

class MyReviewRepository {

    private val service by lazy { RestClient.getReviewService() }

    fun getList(page: Int, sort: MyReviewSortType): ApiLiveData<MyReviewList> {
        return service.getMyReviewList(page, sort.sort, sort.sortOption)
    }
}