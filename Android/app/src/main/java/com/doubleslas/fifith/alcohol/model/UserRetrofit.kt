package com.doubleslas.fifith.alcohol.model

import com.doubleslas.fifith.alcohol.dto.AccessTokenBody
import com.doubleslas.fifith.alcohol.dto.CustomTokenData
import com.doubleslas.fifith.alcohol.model.base.ApiLiveData
import com.doubleslas.fifith.alcohol.dto.RegisterRequestData
import com.doubleslas.fifith.alcohol.dto.SavePoint
import retrofit2.http.*

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


    @PATCH("/user/resign")
    fun withdraw(): ApiLiveData<Any>

}