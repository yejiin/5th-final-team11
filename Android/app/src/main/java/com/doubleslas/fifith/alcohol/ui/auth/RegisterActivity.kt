package com.doubleslas.fifith.alcohol.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.doubleslas.fifith.alcohol.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var activityRegisterBinding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRegisterBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(activityRegisterBinding.root)

    }
}