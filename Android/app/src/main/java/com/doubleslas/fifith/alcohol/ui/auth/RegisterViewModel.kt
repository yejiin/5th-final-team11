package com.doubleslas.fifith.alcohol.ui.auth

import androidx.lifecycle.ViewModel
import com.doubleslas.fifith.alcohol.model.base.ApiLiveData
import com.doubleslas.fifith.alcohol.dto.RegisterRequestData

class RegisterViewModel : ViewModel() {
    private val registerRepository: RegisterRepository =
        RegisterRepository()


    fun nicknameCheck(nickname: String): ApiLiveData<Any> {

        return registerRepository.nicknameCheck(nickname)
    }

    fun register(nickname: String, drink: Float, hangover: Float): ApiLiveData<Any> {
        val registerRequestData: RegisterRequestData = RegisterRequestData(nickname, drink, hangover)
        return registerRepository.register(registerRequestData)
    }


}