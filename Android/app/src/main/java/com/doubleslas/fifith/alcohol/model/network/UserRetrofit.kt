package com.doubleslas.fifith.alcohol.model.network

import com.doubleslas.fifith.alcohol.model.network.base.ApiLiveData
import com.doubleslas.fifith.alcohol.model.network.dto.RegisterRequestData
import com.doubleslas.fifith.alcohol.model.network.dto.SavePoint
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserRetrofit {
    @POST("/user/register")
    fun requestRegister(
        @Body body: RegisterRequestData
    ): ApiLiveData<Any>

    @GET("/user/nickcheck/{nickname}")
    fun nicknameCheck(
        @Path("nickname") nickname: String
    ): ApiLiveData<Any>
    /*
    주량 및 숙취 데이터
     */

    @GET("/user/savepoint")
    fun getSavePoint(): ApiLiveData<SavePoint>
}