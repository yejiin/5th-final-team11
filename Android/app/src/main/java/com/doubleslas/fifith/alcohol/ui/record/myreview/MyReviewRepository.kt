package com.doubleslas.fifith.alcohol.ui.record.myreview

import com.doubleslas.fifith.alcohol.dto.CupboardData
import com.doubleslas.fifith.alcohol.dto.MyReviewData
import com.doubleslas.fifith.alcohol.dto.MyReviewList
import com.doubleslas.fifith.alcohol.model.base.ApiLiveData
import com.doubleslas.fifith.alcohol.model.base.RestClient
import com.doubleslas.fifith.alcohol.sort.enum.MyReviewSortType

class MyReviewRepository {

    private val service by lazy { RestClient.getReviewService() }

    fun getList(page: Int, sort: MyReviewSortType): ApiLiveData<MyReviewList> {
        return service.getMyReviewList(page, sort.sort, sort.sortOption)
    }

    fun deleteList(list: List<MyReviewData>): ApiLiveData<Any> {
        val builder = StringBuilder()
        for (item in list) {
            builder.append(item.rid)
            if (list[list.size - 1] != item) {
                builder.append(',')
            }
        }

        return service.deleteList(builder.toString())
    }
}