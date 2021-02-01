package com.doubleslas.fifith.alcohol.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.doubleslas.fifith.alcohol.App
import com.doubleslas.fifith.alcohol.R
import com.doubleslas.fifith.alcohol.databinding.ActivityLoginBinding
import com.doubleslas.fifith.alcohol.model.base.ApiStatus
import com.doubleslas.fifith.alcohol.ui.main.MainActivity
import com.doubleslas.fifith.alcohol.utils.LogUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.common.KakaoSdk


class LoginActivity : AppCompatActivity() {
    private lateinit var activityLoginBinding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private val loginViewModel by lazy { LoginViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAuth = Firebase.auth

        KakaoSdk.init(this, NATIVE_APP_KEY)


        activityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(activityLoginBinding.root)

        activityLoginBinding.btnCustomFacebook.setOnClickListener {
            activityLoginBinding.btnLoginFacebook.performClick()
        }
        activityLoginBinding.btnBrowse.setOnClickListener {
            /*
                    추천창으로 이동
             */

            // val intent = Intent(this, )

        }


        val googleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()


        val googleSignInClient = GoogleSignIn.getClient(this, googleSignInOption)


        activityLoginBinding.btnCustomGoogle.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, GOOGLE_SIGN_IN)
        }



        activityLoginBinding.btnLoginKakao.setOnClickListener {
            if (LoginClient.instance.isKakaoTalkLoginAvailable(this)) {
                LoginClient.instance.loginWithKakaoTalk(this) { token, error ->
                    if (token != null) {
                        loginViewModel.signInWithKaKao(token, error)
                    }
                }
            } else {
                LoginClient.instance.loginWithKakaoAccount(this) { token, error ->
                    if (token != null) {
                        loginViewModel.signInWithKaKao(token, error)
                    }
                }
            }
        }

        activityLoginBinding.btnLoginFacebook.setPermissions("public_profile", "email")


        observeAuthenticationState()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loginViewModel.facebookAuthCallbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                loginViewModel.firebaseAuthWithGoogle(account?.idToken!!)
            } catch (e: ApiException) {
                LogUtil.e("Auth", "", e)
            }
        }
    }


    private fun observeAuthenticationState() {
        loginViewModel.signInLiveData.observe(this, Observer {
            when (it) {
                is ApiStatus.Success -> {
                    Toast.makeText(applicationContext, "로그인 완료", Toast.LENGTH_SHORT).show()
                    val intent = Intent(
                        applicationContext,
                        when {
                            App.prefs.submitRecommendInfo -> MainActivity::class.java
                            App.prefs.registerUserInfo -> RegisterActivity::class.java
                            else -> RegisterActivity::class.java
                        }
                    )
                    startActivity(intent)
                    finish()
                }
                is ApiStatus.Error -> {
                    Toast.makeText(
                        applicationContext,
                        "로그인 ERROR - ${it.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }


    companion object {
        private const val GOOGLE_SIGN_IN = 1001

        // 카카오 네이티브 앱 키
        private const val NATIVE_APP_KEY = "f4ce9d07643e80606b97e6b009366095"

    }

}

