package com.doubleslas.fifith.alcohol.viewmodel

import androidx.lifecycle.ViewModel
import com.doubleslas.fifith.alcohol.model.network.base.ApiLiveData
import com.doubleslas.fifith.alcohol.model.network.base.MediatorApiLiveData
import com.doubleslas.fifith.alcohol.model.network.dto.RegisterRequestData
import com.doubleslas.fifith.alcohol.model.network.dto.RegisterRequestDataNickName
import com.doubleslas.fifith.alcohol.model.repository.RegisterRepository

class RegisterViewModel : ViewModel() {
    val registerRepository: RegisterRepository = RegisterRepository()
    private val mSignInLiveData = MediatorApiLiveData<String>()


    fun nicknameCheck(nickname: String): ApiLiveData<Any> {
        val registerRequestData: RegisterRequestDataNickName = RegisterRequestDataNickName(nickname)
        return registerRepository.nicknameCheck(registerRequestData.toString())
    }

    fun register(nickname: String, drink: Float, hangover: Float): ApiLiveData<Any> {
        val registerRequestData: RegisterRequestData = RegisterRequestData(nickname, drink, hangover)
        return registerRepository.register(registerRequestData)
    }
}