package com.doubleslas.fifith.alcohol.model.repository

import com.doubleslas.fifith.alcohol.model.network.base.ApiLiveData
import com.doubleslas.fifith.alcohol.model.network.base.RestClient
import com.doubleslas.fifith.alcohol.model.network.dto.SearchList

class SearchRepository {
    private val searchRetrofit by lazy { RestClient.getSearchService() }

    fun getList(
        category: String,
        page: Int,
        sort: String,
        sortOption: String
    ): ApiLiveData<SearchList> {
        return searchRetrofit.getList(category, page, sort, sortOption)
    }
}