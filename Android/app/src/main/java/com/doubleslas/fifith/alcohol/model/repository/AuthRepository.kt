package com.doubleslas.fifith.alcohol.model.repository

import com.doubleslas.fifith.alcohol.model.network.base.ApiStatus
import com.doubleslas.fifith.alcohol.model.network.base.MutableRetroLiveData
import com.doubleslas.fifith.alcohol.model.network.base.RetroLiveData
import com.google.firebase.auth.AuthCredential

class AuthRepository {
    fun completeLogin(credential: AuthCredential): RetroLiveData<Any> {
        val result = MutableRetroLiveData<Any>()
        result.value = ApiStatus.Loading

        return result
    }
}