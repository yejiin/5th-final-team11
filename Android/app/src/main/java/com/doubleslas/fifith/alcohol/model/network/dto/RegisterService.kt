package com.doubleslas.fifith.alcohol.model.network.dto

import android.telecom.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RegisterService {
    @FormUrlEncoded
    @POST("/user/register")
    fun requestRegister(
        @Field("nickname") nickname: String
    ): retrofit2.Call<Register>
}