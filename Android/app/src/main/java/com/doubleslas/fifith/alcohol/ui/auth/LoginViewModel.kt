package com.doubleslas.fifith.alcohol.ui.auth

import androidx.lifecycle.ViewModel
import com.doubleslas.fifith.alcohol.model.base.ApiLiveData
import com.doubleslas.fifith.alcohol.model.base.ApiStatus
import com.doubleslas.fifith.alcohol.model.base.MediatorApiLiveData
import com.doubleslas.fifith.alcohol.utils.LogUtil
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.kakao.sdk.auth.model.OAuthToken

class LoginViewModel : ViewModel() {
    private val authRepository by lazy { AuthRepository() }
    private var firebaseAuth = FirebaseAuth.getInstance()

    private val mSignInLiveData = MediatorApiLiveData<String>()
    val signInLiveData: ApiLiveData<String> = mSignInLiveData

    val facebookAuthCallbackManager by lazy {
        CallbackManager.Factory.create().also {
            LoginManager.getInstance().registerCallback(it,
                object : FacebookCallback<LoginResult?> {
                    override fun onSuccess(loginResult: LoginResult?) {
                        loginResult?.let { result ->
                            val token = result.accessToken.token
                            val credential = FacebookAuthProvider.getCredential(token)
                            val result = authRepository.signInWithCredential(credential)
                            mSignInLiveData.addSource(result)
                        }
                    }

                    override fun onCancel() {
                    }

                    override fun onError(exception: FacebookException) {
                        LogUtil.e("Auth", "", exception)
                        mSignInLiveData.value = ApiStatus.Error(-1, exception.message ?: "Error Facebook Auth")
                    }
                })
        }
    }

    fun firebaseAuthWithGoogle(idToken: String) {
        mSignInLiveData.value = ApiStatus.Loading
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential).apply {
            addOnSuccessListener {
                val result = authRepository.signInWithCredential(credential)
                mSignInLiveData.addSource(result)
            }
            addOnFailureListener {
                LogUtil.e("Auth", "", it)
                mSignInLiveData.value = ApiStatus.Error(-1, it.message ?: "Error Google Auth")
            }
        }
    }

    fun signInWithKaKao(token: OAuthToken, error: Throwable?) {
//        mSignInLiveData.value = ApiStatus.Loading
        // 액세스 토큰과 리프레시 토큰값 단순 출력
        LogUtil.d("Auth", "$token $error")

        val result = authRepository.signInWithKakaoToken(token.accessToken)
        mSignInLiveData.addSource(result)
    }

}