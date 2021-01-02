package com.doubleslas.fifith.alcohol.viewmodel

import androidx.lifecycle.ViewModel
import com.doubleslas.fifith.alcohol.model.network.base.ApiLiveData
import com.doubleslas.fifith.alcohol.model.network.base.MediatorApiLiveData
import com.doubleslas.fifith.alcohol.model.network.dto.RegisterRequestData
import com.doubleslas.fifith.alcohol.model.repository.RegisterRepository

class RegisterViewModel : ViewModel() {
    val registerRepository: RegisterRepository = RegisterRepository()
    private val mSignInLiveData = MediatorApiLiveData<String>()


    fun nicknameCheck(nickname: String): ApiLiveData<Any> {
        var registerRequestData: RegisterRequestData = RegisterRequestData(nickname)
        return registerRepository.nicknameCheck(registerRequestData)
    }

    fun register(nickname: String) :ApiLiveData<Any> {
        val registerRequestData: RegisterRequestData = RegisterRequestData(nickname)
        return registerRepository.register(registerRequestData)
    }
}