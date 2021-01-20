package com.doubleslas.fifith.alcohol.model.network

import com.doubleslas.fifith.alcohol.model.network.base.ApiLiveData
import com.doubleslas.fifith.alcohol.model.network.dto.ReviewData
import com.doubleslas.fifith.alcohol.model.network.dto.ReviewList
import com.doubleslas.fifith.alcohol.model.network.dto.WriteReviewData
import retrofit2.http.*

interface ReviewRetrofit {
    @POST("/review")
    fun writeReview(
        @Query("aid") aid: Int,
        @Body data: WriteReviewData
    ): ApiLiveData<Any>

    @GET("/review/list")
    fun readReview(
        @Query("aid") aid: Int,
        @Query("reviewPage") reviewPage: Int
    ): ApiLiveData<ReviewList>


    @PUT("/review/{rid}/love")
    fun clickLike(
        @Path("rid") rid: Int,
        @Body loveClick: Boolean
    ): ApiLiveData<Any>

    @PUT("/review/comment/{rid}")
    fun writeComment(
        @Path("rid") rid: Int,
        @Body content: String
    ): ApiLiveData<Any>
}