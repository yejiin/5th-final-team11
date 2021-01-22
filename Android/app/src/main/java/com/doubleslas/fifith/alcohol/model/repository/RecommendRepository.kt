package com.doubleslas.fifith.alcohol.model.repository

import com.doubleslas.fifith.alcohol.enum.RecommendSortType
import com.doubleslas.fifith.alcohol.model.network.base.ApiLiveData
import com.doubleslas.fifith.alcohol.model.network.base.RestClient
import com.doubleslas.fifith.alcohol.model.network.dto.RecommendList

class RecommendRepository {
    private val service by lazy { RestClient.getRecommendService() }

    fun getList(category: String, sort: RecommendSortType): ApiLiveData<RecommendList> {
        return service.getRecommendList(category, sort.sort, sort.sortOption)
    }
}
