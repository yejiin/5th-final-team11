package com.doubleslas.fifith.alcohol.model.repository

import com.doubleslas.fifith.alcohol.App
import com.doubleslas.fifith.alcohol.enum.SortType
import com.doubleslas.fifith.alcohol.model.network.base.ApiLiveData
import com.doubleslas.fifith.alcohol.model.network.base.RestClient
import com.doubleslas.fifith.alcohol.model.network.dto.SearchList
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SearchRepository {
    private val searchRetrofit by lazy { RestClient.getSearchService() }

    fun getList(
        category: String,
        page: Int,
        sort: SortType
    ): ApiLiveData<SearchList> {
        return searchRetrofit.getList(category, page, sort.sort, sort.sortOption)
    }

    fun search(
        keyword: String,
        page: Int,
        sort: SortType
    ): ApiLiveData<SearchList> {
        return searchRetrofit.searchAlcohol(keyword, page, sort.sort, sort.sortOption)
    }

    fun getSearchHistory(): MutableList<String> {
        val json = App.prefs.searchHistoryList
        val typeToken = object : TypeToken<ArrayList<String>>() {}
        return Gson().fromJson(json, typeToken.type)
    }

    fun saveSearchHistory(list: List<String>) {
        App.prefs.searchHistoryList = Gson().toJson(list)
    }

}