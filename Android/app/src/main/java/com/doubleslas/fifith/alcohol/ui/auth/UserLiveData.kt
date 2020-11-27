package com.doubleslas.fifith.alcohol.ui.auth

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class UserLiveData : LiveData<FirebaseUser?>() {
    private val userAuth = FirebaseAuth.getInstance()

    private val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
        value = firebaseAuth.currentUser
    }

    override fun onActive() {
        super.onActive()
        userAuth.addAuthStateListener(authStateListener)
    }

    override fun onInactive() {
        super.onInactive()
        userAuth.removeAuthStateListener(authStateListener)
    }
}