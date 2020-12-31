package com.doubleslas.fifith.alcohol.model.network

import com.doubleslas.fifith.alcohol.model.network.base.ApiLiveData
import com.doubleslas.fifith.alcohol.model.network.dto.RegisterRequestData
import retrofit2.http.Body
import retrofit2.http.POST

interface UserRetrofit {
    @POST("/user/register")
    fun requestRegister(
        @Body body: RegisterRequestData
    ): ApiLiveData<Any>
}