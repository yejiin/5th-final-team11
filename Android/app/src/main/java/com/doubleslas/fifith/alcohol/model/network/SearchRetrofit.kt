package com.doubleslas.fifith.alcohol.model.network

import com.doubleslas.fifith.alcohol.model.network.base.ApiLiveData
import com.doubleslas.fifith.alcohol.model.network.dto.AlcoholList
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchRetrofit {
    @GET("/alcohol")
    fun getList(
        @Query("category") category: String,
        @Query("sort") sort: String,
        @Query("sortOption") sortOption: String
    ): ApiLiveData<AlcoholList>


    @GET("/alcohol/serach")
    fun searchAlcohol(
        @Query("category") category: String,
        @Query("sort") sort: String,
        @Query("sortOption") sortOption: String
    ): ApiLiveData<AlcoholList>
}