package com.doubleslas.fifith.alcohol.model.network

import com.doubleslas.fifith.alcohol.model.network.base.ApiLiveData
import com.doubleslas.fifith.alcohol.model.network.dto.SearchList
import com.doubleslas.fifith.alcohol.model.network.dto.WriteReviewData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ReviewRetrofit {
    @POST("/review/")
    fun writeReview(
        @Body data: WriteReviewData
    ): ApiLiveData<Any>
}