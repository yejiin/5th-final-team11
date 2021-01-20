package com.doubleslas.fifith.alcohol.model.network

import com.doubleslas.fifith.alcohol.model.network.base.ApiLiveData
import com.doubleslas.fifith.alcohol.model.network.dto.recommend.RecommendInfoData
import retrofit2.http.Body
import retrofit2.http.POST

interface RecommendRetrofit {
    @POST("/alcohol/recommend")
    fun submitRecommendInfo(
        @Body data: RecommendInfoData
    ): ApiLiveData<Any>
}