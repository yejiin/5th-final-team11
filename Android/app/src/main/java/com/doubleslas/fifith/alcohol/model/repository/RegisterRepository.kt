package com.doubleslas.fifith.alcohol.model.repository

import com.doubleslas.fifith.alcohol.model.network.AuthRetrofit
import com.doubleslas.fifith.alcohol.model.network.UserRetrofit
import com.doubleslas.fifith.alcohol.model.network.base.ApiLiveData
import com.doubleslas.fifith.alcohol.model.network.base.RestClient
import com.doubleslas.fifith.alcohol.model.network.base.RestClient.getAuthService
import com.doubleslas.fifith.alcohol.model.network.base.RestClient.getUserService
import com.doubleslas.fifith.alcohol.model.network.dto.RegisterRequestData

class RegisterRepository {
    val retrofit: UserRetrofit = getUserService()
    fun register(registerRequestData: RegisterRequestData):ApiLiveData<Any> {
        return retrofit.requestRegister(registerRequestData)
    }
}