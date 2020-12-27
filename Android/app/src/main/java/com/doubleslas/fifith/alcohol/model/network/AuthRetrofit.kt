package com.doubleslas.fifith.alcohol.model.network

import com.doubleslas.fifith.alcohol.model.network.base.ApiLiveData
import retrofit2.http.GET

interface AuthRetrofit {
    @GET("/test")
    fun test(): ApiLiveData<Any>
}