package com.doubleslas.fifith.alcohol.model

import com.doubleslas.fifith.alcohol.dto.DetailData
import com.doubleslas.fifith.alcohol.model.base.ApiLiveData
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailRetrofit {
    @GET("/alcohol/{id}")
    fun getDetail(
        @Path("id") id: Int
    ): ApiLiveData<DetailData>
}