package com.doubleslas.fifith.alcohol.ui.auth.firstinput

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.doubleslas.fifith.alcohol.R
import com.doubleslas.fifith.alcohol.databinding.ActivityFirstInfoBinding

class FirstInputActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFirstInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFirstInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, DrinkTypeSelectFragment())
            .commit()
    }

    fun openSecondPage() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, DetailInfoInputFragment())
            .addToBackStack(null)
            .commit()
    }
}