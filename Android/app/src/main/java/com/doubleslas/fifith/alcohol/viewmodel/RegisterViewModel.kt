package com.doubleslas.fifith.alcohol.viewmodel

import androidx.lifecycle.ViewModel
import com.doubleslas.fifith.alcohol.model.network.base.ApiLiveData
import com.doubleslas.fifith.alcohol.model.network.dto.RegisterRequestData
import com.doubleslas.fifith.alcohol.model.repository.RegisterRepository

class RegisterViewModel : ViewModel() {
    private val registerRepository: RegisterRepository = RegisterRepository()


    fun nicknameCheck(nickname: String): ApiLiveData<Any> {

        return registerRepository.nicknameCheck(nickname)
    }

    fun register(nickname: String, drink: Float, hangover: Float): ApiLiveData<Any> {
        val registerRequestData: RegisterRequestData = RegisterRequestData(nickname, drink, hangover)
        return registerRepository.register(registerRequestData)
    }


}