package com.doubleslas.fifith.alcohol.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.doubleslas.fifith.alcohol.databinding.ActivityAgeCheckBinding

class AgeCheckActivity : AppCompatActivity() {
    private lateinit var activityAgeCheckBinding: ActivityAgeCheckBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityAgeCheckBinding = ActivityAgeCheckBinding.inflate(layoutInflater)
        setContentView(activityAgeCheckBinding.root)

        activityAgeCheckBinding.btnStart.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        activityAgeCheckBinding.btnStop.setOnClickListener {
            finish()
            System.exit(0)
        }
    }

}

