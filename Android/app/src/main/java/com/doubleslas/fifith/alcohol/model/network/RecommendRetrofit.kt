package com.doubleslas.fifith.alcohol.model.network

import com.doubleslas.fifith.alcohol.model.network.base.ApiLiveData
import com.doubleslas.fifith.alcohol.model.network.dto.RecommendList
import com.doubleslas.fifith.alcohol.model.network.dto.recommend.RecommendInfoData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RecommendRetrofit {
    @POST("/alcohol/recommend")
    fun submitRecommendInfo(
        @Body data: RecommendInfoData
    ): ApiLiveData<Any>

    @GET("/alcohol/recommend")
    fun getRecommendList(
        @Query("category") category: String,
        @Query("sort") sort: String,
        @Query("sortOption") sortOption: String
    ): ApiLiveData<RecommendList>
}