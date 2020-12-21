package com.doubleslas.fifith.alcohol.ui.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.doubleslas.fifith.alcohol.model.repository.AuthRepository
import com.doubleslas.fifith.alcohol.utils.LogUtil
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginViewModel : ViewModel() {
    private val authRepository by lazy { AuthRepository() }
    private var firebaseAuth = FirebaseAuth.getInstance()


    val facebookAuthCallbackManager by lazy {
        CallbackManager.Factory.create().also {
            LoginManager.getInstance().registerCallback(it,
                object : FacebookCallback<LoginResult?> {
                    override fun onSuccess(loginResult: LoginResult?) {
                        loginResult?.let { result ->
                            val token = result.accessToken.token
                            LogUtil.d("Facebook", token)
                            val credential = FacebookAuthProvider.getCredential(token)
                            authRepository.completeLogin(credential)
                        }
                    }

                    override fun onCancel() {
                        // App code
                    }

                    override fun onError(exception: FacebookException) {
                        LogUtil.e("Facebook", "Login Error", exception)
                        // App code
                    }
                })
        }
    }

    enum class AuthenticationState {
        AUTHENTICATED, UNAUTHENTICATED, INVALID_AUTHENTICATION
    }

    val authenticationState = FirebaseUserLiveData().map { user ->
        if (user != null) {
            AuthenticationState.AUTHENTICATED
        } else {
            AuthenticationState.UNAUTHENTICATED
        }
    }

    fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth!!.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser?.displayName
                    authRepository.completeLogin(credential)
                    Log.d("junmin", user.toString())
                }
            }
    }

}