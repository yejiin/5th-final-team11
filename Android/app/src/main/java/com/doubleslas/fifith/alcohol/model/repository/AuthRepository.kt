package com.doubleslas.fifith.alcohol.model.repository

import android.util.Log
import androidx.lifecycle.Observer
import com.doubleslas.fifith.alcohol.App
import com.doubleslas.fifith.alcohol.model.network.base.*
import com.doubleslas.fifith.alcohol.model.network.dto.AccessTokenBody
import com.doubleslas.fifith.alcohol.utils.LogUtil
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthRepository {

    private val firebaseAuth by lazy { Firebase.auth }
    private val authService by lazy { RestClient.getAuthService() }

    fun signInWithCredential(credential: AuthCredential): ApiLiveData<Any> {
        val result = MediatorApiLiveData<Any>()
        result.value = ApiStatus.Loading

        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val liveData = getIdToken()

                    result.addSource(liveData, Observer { idToken ->
                        if (idToken !is ApiStatus.Loading) result.removeSource(liveData)
                        result.value = idToken
                    })

                } else {
                    // If sign in fails, display a message to the user.
                    LogUtil.e("FirebaseAuth", "signInWithCredential:failure", it.exception!!)
                    result.value = ApiStatus.Error(-1, "Firebase Error")
                }
            }

        return result
    }

    fun signInWithKakaoToken(accessToken: String) {
        val mediator = MediatorApiLiveData<String>()
        val liveData = authService.loginKaKao(AccessTokenBody(accessToken))

        mediator.addSource(liveData, Observer {
            when (it) {
                is ApiStatus.Success -> {
                    signInWithCustomToken(it.data.customToken)
                }
            }
        })
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
                LogUtil.d("FirebaseToken", idToken)
                App.prefs.idToken = idToken
                result.value = ApiStatus.Success(0, idToken)
                authService.test()
            } else {
                result.value = ApiStatus.Error(0, "Error Get Id Token")
            }
        }

        return result
    }

    private fun signInWithCustomToken(customToken: String) {
        firebaseAuth.signInWithCustomToken(customToken).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("kakao", "signInWithCustomToken : success")
                getIdToken()
            } else {
                Log.d("kakao", "signInWithCustomToken : failed")
            }
        }
    }
}