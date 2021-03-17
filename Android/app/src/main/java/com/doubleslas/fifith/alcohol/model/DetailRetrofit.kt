package com.doubleslas.fifith.alcohol.model

import com.doubleslas.fifith.alcohol.dto.DetailData
import com.doubleslas.fifith.alcohol.dto.FavoriteBody
import com.doubleslas.fifith.alcohol.model.base.ApiLiveData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface DetailRetrofit {
    @GET("/alcohol/{id}")
    fun getDetail(
        @Path("id") id: Int
    ): ApiLiveData<DetailData>

    @PUT("/alcohol/{id}/love")
    fun setFavorite(
        @Path("id") id: Int,
        @Body body: FavoriteBody
    ): ApiLiveData<Any>
}