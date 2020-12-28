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

    private val callback by lazy {
        object : MediatorApiCallback<String> {
            override fun onSuccess(data: String) {
                super.onSuccess(data)
            }

            override fun onError(code: Int, msg: String) {
                super.onError(code, msg)
            }
        }
    }

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
                LogUtil.e("FirebaseAuth", "signInWithCredential:failure", it)
                mediator.value =
                    ApiStatus.Error(ErrorCode.SigInFirebase.code, it.message ?: "Firebase Error")
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
                LogUtil.e("kakao", "signInWithCustomToken : failed", it)
                mediator.value =
                    ApiStatus.Error(ErrorCode.SigInFirebase.code, it.message ?: "Custom Token")
            }
        }


        return mediator
    }

    private fun getIdToken(): ApiLiveData<String> {
        val result = MutableApiLiveData<String>()

        val user = firebaseAuth.currentUser

        if (user == null) {
            result.value = ApiStatus.Error(-1, "Not Login")
            return result
        }

        result.value = ApiStatus.Loading
        user.getIdToken(true).addOnCompleteListener {
            if (it.isSuccessful) {
                val idToken = it.result?.token ?: ""
                App.prefs.idToken = idToken
                result.value = ApiStatus.Success(0, idToken)
                authService.test() // TODO: Remove
            } else {
                result.value = ApiStatus.Error(0, "Error Get Id Token")
            }
        }

        return result
    }


    enum class ErrorCode(val code: Int) {
        NotLogin(-1),
        GetIdToken(-2),
        SigInFirebase(-3),
        SignInKakao(-4)
    }
}