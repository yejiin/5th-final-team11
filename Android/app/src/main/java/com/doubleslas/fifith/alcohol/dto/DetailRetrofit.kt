package com.doubleslas.fifith.alcohol.dto

import com.doubleslas.fifith.alcohol.model.base.ApiLiveData
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailRetrofit {
    @GET("/alcohol/detail/{id}")
    fun getDetail(
        @Path("id") id: Int
    ): ApiLiveData<DetailList>
}