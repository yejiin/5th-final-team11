package com.doubleslas.fifith.alcohol.model.network

import com.doubleslas.fifith.alcohol.model.network.base.ApiLiveData
import com.doubleslas.fifith.alcohol.model.network.dto.AccessTokenBody
import com.doubleslas.fifith.alcohol.model.network.dto.CustomTokenData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthRetrofit {
    @GET("/test")
    fun test(): ApiLiveData<Any>

    @POST("/auth/kakao")
    fun loginKaKao(@Body token: AccessTokenBody): ApiLiveData<CustomTokenData>

}

