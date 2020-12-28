package com.doubleslas.fifith.alcohol.model.repository

import com.doubleslas.fifith.alcohol.App
import com.doubleslas.fifith.alcohol.model.network.base.*
import com.doubleslas.fifith.alcohol.model.network.dto.AccessTokenBody
import com.doubleslas.fifith.alcohol.model.network.dto.CustomTokenData
import com.doubleslas.fifith.alcohol.utils.LogUtil
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthRepository {

    private val firebaseAuth by lazy { Firebase.auth }
    private val authService by lazy { RestClient.getAuthService() }

    fun signInWithCredential(credential: AuthCredential): ApiLiveData<String> {
        val mediator = MediatorApiLiveData<String>()
        mediator.value = ApiStatus.Loading

        firebaseAuth.signInWithCredential(credential).apply {
            addOnSuccessListener {
                val liveData = getIdToken()
                mediator.addSource(liveData)
            }
            addOnFailureListener {
                // If sign in fails, display a message to the user.
                LogUtil.e("Auth", "signInWithCredential:failure", it)
                mediator.value =
                    ApiStatus.Error(ERROR_CODE_UNKNOWN, it.message ?: "Firebase Error")
            }
        }

        return mediator
    }

    fun signInWithKakaoToken(accessToken: String): ApiLiveData<String> {
        val mediator = MediatorApiLiveData<String>()
        val liveData = authService.loginKaKao(AccessTokenBody(accessToken))

        mediator.value = ApiStatus.Loading

        mediator.addSource(liveData, object : MediatorApiCallback<CustomTokenData> {
            override fun onSuccess(data: CustomTokenData) {
                val firebaseLiveData = signInWithCustomToken(data.customToken)
                mediator.addSource(firebaseLiveData)
            }

            override fun onError(code: Int, msg: String) {
                mediator.value = ApiStatus.Error(code, msg)
            }
        })

        return mediator
    }


    private fun signInWithCustomToken(customToken: String): ApiLiveData<String> {
        val mediator = MediatorApiLiveData<String>()

        mediator.value = ApiStatus.Loading
        val task = firebaseAuth.signInWithCustomToken(customToken).apply {
            addOnSuccessListener {
                val idTokenLiveData = getIdToken()
                mediator.addSource(idTokenLiveData)
            }
            addOnFailureListener {
                LogUtil.e("Auth", "signInWithCustomToken : failed", it)
                mediator.value =
                    ApiStatus.Error(ERROR_CODE_UNKNOWN, it.message ?: "Custom Token")
            }
        }


        return mediator
    }

    private fun getIdToken(): ApiLiveData<String> {
        val result = MutableApiLiveData<String>()

        val user = firebaseAuth.currentUser

        if (user == null) {
            result.value = ApiStatus.Error(ERROR_CODE_NOT_LOGIN, "Not Login")
            return result
        }

        result.value = ApiStatus.Loading
        user.getIdToken(true).addOnCompleteListener {
            if (it.isSuccessful) {
                val idToken = it.result?.token ?: ""
                App.prefs.idToken = idToken
                result.value = ApiStatus.Success(200, idToken)
                authService.test() // TODO: Remove
            } else {
                result.value = ApiStatus.Error(ERROR_CODE_UNKNOWN, "Error Get Id Token")
            }
        }

        return result
    }

    companion object {
        const val ERROR_CODE_NOT_LOGIN = -1
        const val ERROR_CODE_UNKNOWN = -2
    }
}