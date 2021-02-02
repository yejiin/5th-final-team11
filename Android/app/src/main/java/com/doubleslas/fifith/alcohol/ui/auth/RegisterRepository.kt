package com.doubleslas.fifith.alcohol.ui.auth

import com.doubleslas.fifith.alcohol.model.UserRetrofit
import com.doubleslas.fifith.alcohol.model.base.ApiLiveData
import com.doubleslas.fifith.alcohol.model.base.RestClient.getUserService
import com.doubleslas.fifith.alcohol.dto.RegisterRequestData

class RegisterRepository {
    val retrofit: UserRetrofit = getUserService()
    fun nicknameCheck(nickname: String): ApiLiveData<Any> {
        return retrofit.nicknameCheck(nickname)
    }

    fun register(registerRequestData: RegisterRequestData): ApiLiveData<Any> {
        return retrofit.requestRegister(registerRequestData)
    }
}