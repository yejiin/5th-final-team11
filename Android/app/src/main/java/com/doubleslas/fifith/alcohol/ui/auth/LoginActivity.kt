package com.doubleslas.fifith.alcohol.ui.auth

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.doubleslas.fifith.alcohol.App
import com.doubleslas.fifith.alcohol.R
import com.doubleslas.fifith.alcohol.databinding.ActivityLoginBinding
import com.doubleslas.fifith.alcohol.model.base.ApiStatus
import com.doubleslas.fifith.alcohol.ui.common.ProgressDialog
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
    private val loginViewModel by lazy { ViewModelProvider(this).get(LoginViewModel::class.java) }
    private val progressDialog by lazy {
        ProgressDialog().apply {
            isCancelable = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAuth = Firebase.auth

        KakaoSdk.init(this, NATIVE_APP_KEY)


        activityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(activityLoginBinding.root)


        val strSubTitle = getString(R.string.login_sub_title) + "\n"
        val strMainTitle = getString(R.string.login_main_title)
        val str: SpannableStringBuilder = SpannableStringBuilder(strSubTitle + strMainTitle)
        str.setSpan(
            StyleSpan(Typeface.BOLD),
            strSubTitle.length,
            str.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
//        activityLoginBinding.tvTitle.text = str;

        activityLoginBinding.btnFacebook.setOnClickListener {
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


        activityLoginBinding.btnGoogle.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, GOOGLE_SIGN_IN)
        }



        activityLoginBinding.btnKakao.setOnClickListener {
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
                is ApiStatus.Loading -> {
                    if(!progressDialog.isVisible) progressDialog.show(supportFragmentManager, null)
                }
                is ApiStatus.Success -> {
                    progressDialog.dismiss()
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
                    progressDialog.dismiss()
                    Toast.makeText(
                        applicationContext,
                        "로그인 도중 문제가 발생하였습니다. 다시 시도해주세요.",
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

