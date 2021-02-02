package com.doubleslas.fifith.alcohol.model

import com.doubleslas.fifith.alcohol.model.base.ApiLiveData
import com.doubleslas.fifith.alcohol.dto.AccessTokenBody
import com.doubleslas.fifith.alcohol.dto.CustomTokenData
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthRetrofit {

    @POST("/auth/kakao")
    fun loginKaKao(@Body token: AccessTokenBody): ApiLiveData<CustomTokenData>

}

