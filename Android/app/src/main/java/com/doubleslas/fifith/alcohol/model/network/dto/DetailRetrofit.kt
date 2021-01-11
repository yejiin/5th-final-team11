package com.doubleslas.fifith.alcohol.model.network.dto

import com.doubleslas.fifith.alcohol.model.network.base.ApiLiveData
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailRetrofit {
    @GET("/alcohol/detail/{id}")
    fun getDetail(
        @Path("id") id: Int
    ): ApiLiveData<Any>
}