package com.doubleslas.fifith.alcohol

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.doubleslas.fifith.alcohol.databinding.ActivityMainBinding
import com.doubleslas.fifith.alcohol.model.network.base.ApiStatus
import com.doubleslas.fifith.alcohol.model.network.base.MutableApiLiveData
import com.doubleslas.fifith.alcohol.ui.LoginActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {

    private var activityMainBinding: ActivityMainBinding? = null
    lateinit var googleApiClient: GoogleApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        activityMainBinding = binding
        setContentView(activityMainBinding!!.root)

        googleApiClient = GoogleApiClient.Builder(applicationContext)
            .enableAutoManage(this, this)
            .addApi(Auth.GOOGLE_SIGN_IN_API)
            .build()

        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser == null) {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
        } else {

        }

        btn_logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            Auth.GoogleSignInApi.signOut(googleApiClient)
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
        }
        val liveData = MutableApiLiveData<String>()

        liveData.observe(this, Observer {
            when (it) {
                is ApiStatus.Loading -> {

                }
                is ApiStatus.Success<*> -> {

                }
                is ApiStatus.Error -> {

                }
            }
        })
    }

    override fun onResume() {
        super.onResume()

        val currentUser = FirebaseAuth.getInstance().currentUser
        supportActionBar?.title = currentUser.toString()
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("Not yet implemented")
    }


}