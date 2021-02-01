package com.doubleslas.fifith.alcohol.model

import com.doubleslas.fifith.alcohol.model.base.ApiLiveData
import com.doubleslas.fifith.alcohol.dto.SearchList
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchRetrofit {
    @GET("/alcohol")
    fun getList(
        @Query("category") category: String,
        @Query("page") page: Int,
        @Query("sort") sort: String,
        @Query("sortOption") sortOption: String
    ): ApiLiveData<SearchList>


    @GET("/alcohol/search")
    fun searchAlcohol(
        @Query("keyword") keyword: String,
        @Query("page") page: Int,
        @Query("sort") sort: String,
        @Query("sortOption") sortOption: String
    ): ApiLiveData<SearchList>
}