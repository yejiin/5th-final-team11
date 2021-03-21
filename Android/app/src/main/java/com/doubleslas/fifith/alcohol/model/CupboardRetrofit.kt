package com.doubleslas.fifith.alcohol.model

import com.doubleslas.fifith.alcohol.dto.CupboardList
import com.doubleslas.fifith.alcohol.model.base.ApiLiveData
import retrofit2.http.GET
import retrofit2.http.Query

interface CupboardRetrofit {
    @GET("/cabinet")
    fun getCupboardList(
        @Query("page") page: Int,
        @Query("sort") sort: String,
        @Query("sortOption") sortOption: String
    ): ApiLiveData<CupboardList>

    @GET("/cabinet/love")
    fun getLoveList(
        @Query("page") page: Int,
        @Query("sort") sort: String,
        @Query("sortOption") sortOption: String
    ): ApiLiveData<CupboardList>
}