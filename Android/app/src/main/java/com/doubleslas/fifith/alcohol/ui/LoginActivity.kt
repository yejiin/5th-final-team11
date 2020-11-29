package com.doubleslas.fifith.alcohol.ui

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.doubleslas.fifith.alcohol.MainActivity
import com.doubleslas.fifith.alcohol.R
import com.doubleslas.fifith.alcohol.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private var googleSignInClient: GoogleSignInClient? = null
    private var activityLoginBinding: ActivityLoginBinding? = null
    private var firebaseAuth: FirebaseAuth? = null
    private val RC_SIGN_IN = 1001
    private val viewModel = LoginViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityLoginBinding.inflate(layoutInflater)
        activityLoginBinding = binding
        setContentView(activityLoginBinding!!.root)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        firebaseAuth = FirebaseAuth.getInstance()


        btn_login.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

        btn_signout.setOnClickListener {
            firebaseAuth!!.signOut()
            googleSignInClient.signOut()
        }



        observeAuthenticationState()


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account?.idToken!!)
                Log.d("junmin", "sign in success" + account.id)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } catch (e: ApiException) {
                Log.d("junmin", "sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    val user = firebaseAuth?.currentUser
                    Log.d("junmin", "sign in credential success")
                } else {
                    Log.d("junmin", "sign in credential failed")
                }
            }
    }

    private fun observeAuthenticationState() {
        viewModel.authenticationState.observe(this, Observer { authenticationState ->
            when (authenticationState) {
                LoginViewModel.AuthenticationState.AUTHENTICATED -> {
                    activityLoginBinding?.tvState?.text = "로그인 되어있음"
                    activityLoginBinding?.btnLogin?.visibility = View.GONE
                }
                LoginViewModel.AuthenticationState.UNAUTHENTICATED -> {
                    activityLoginBinding?.tvState?.text = "로그인 안됨"
                    activityLoginBinding?.btnLogin?.visibility = View.VISIBLE
            }
        }
            })
    }

}

