package com.doubleslas.fifith.alcohol.ui

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginViewModel : ViewModel() {
    private var firebaseAuth = FirebaseAuth.getInstance()

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
            .addOnCompleteListener {task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                } else {
                    Log.d("junmin","실패(크레덴셜)")
                }
            }
    }

}