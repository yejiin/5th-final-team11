package com.doubleslas.fifith.alcohol.model.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.doubleslas.fifith.alcohol.App
import com.doubleslas.fifith.alcohol.model.network.base.ApiLiveData
import com.doubleslas.fifith.alcohol.model.network.base.ApiStatus
import com.doubleslas.fifith.alcohol.model.network.base.MediatorApiLiveData
import com.doubleslas.fifith.alcohol.model.network.base.MutableApiLiveData
import com.doubleslas.fifith.alcohol.utils.LogUtil
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthRepository {

    private val firebaseAuth by lazy { Firebase.auth }

    fun completeLogin(credential: AuthCredential): ApiLiveData<Any> {
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
                    LogUtil.w("FirebaseAuth", "signInWithCredential:failure", it.exception!!)
                    result.value = ApiStatus.Error(-1, "Firebase Error")
                }
            }

        return result
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
            } else {
                result.value = ApiStatus.Error(0, "Error Get Id Token")
            }
        }

        return result
    }
}