package com.doubleslas.fifith.alcohol.ui.auth

import androidx.lifecycle.ViewModel
import com.doubleslas.fifith.alcohol.dto.RegisterRequestData
import com.doubleslas.fifith.alcohol.model.base.ApiLiveData
import com.doubleslas.fifith.alcohol.model.base.ApiStatus
import com.doubleslas.fifith.alcohol.model.base.MediatorApiLiveData
import com.doubleslas.fifith.alcohol.model.base.MediatorApiSuccessCallback

class RegisterViewModel : ViewModel() {
    private val registerRepository: RegisterRepository =
        RegisterRepository()

    var nickname: String? = null
        private set;


    fun nicknameCheck(nickname: String): ApiLiveData<Any> {
        val mediatorApiLiveData = MediatorApiLiveData<Any>()
        val liveData = registerRepository.nicknameCheck(nickname)

        this.nickname = null
        mediatorApiLiveData.addSource(liveData, object : MediatorApiSuccessCallback<Any> {
            override fun onSuccess(code: Int, data: Any) {
                this@RegisterViewModel.nickname = nickname
                mediatorApiLiveData.value = ApiStatus.Success(code, data)
            }
        })
        return mediatorApiLiveData
    }

    fun register(nickname: String, drink: Float, hangover: Float): ApiLiveData<Any> {
        val registerRequestData: RegisterRequestData =
            RegisterRequestData(nickname, drink, hangover)
        return registerRepository.register(registerRequestData)
    }


}