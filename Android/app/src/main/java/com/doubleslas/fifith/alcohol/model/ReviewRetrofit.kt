package com.doubleslas.fifith.alcohol.model

import com.doubleslas.fifith.alcohol.dto.*
import com.doubleslas.fifith.alcohol.model.base.ApiLiveData
import retrofit2.http.*

interface ReviewRetrofit {
    @GET("/review")
    fun getMyReviewList(
        @Query("page") page: Int,
        @Query("sort") sort: String,
        @Query("sortOption") sortOption: String
    ): ApiLiveData<MyReviewList>

    @POST("/review")
    fun writeReview(
        @Query("aid") aid: Int,
        @Body data: WriteReviewData
    ): ApiLiveData<Any>

    @DELETE("/review/{rid}")
    fun writeReview(

    ): ApiLiveData<Any>

    @GET("/review/list")
    fun readReview(
        @Query("aid") aid: Int,
        @Query("page") reviewPage: Int
    ): ApiLiveData<ReviewList>


    @PUT("/review/{rid}/love")
    fun likeReview(
        @Path("rid") rid: Int,
        @Body loveClick: ReviewLikeBody
    ): ApiLiveData<Any>

    @POST("/review/{rid}/comment")
    fun writeComment(
        @Path("rid") rid: Int,
        @Body content: ReviewCommentBody
    ): ApiLiveData<Any>
}