package com.doubleslas.fifith.alcohol.ui.auth

import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import androidx.lifecycle.Observer
import com.doubleslas.fifith.alcohol.App
import com.doubleslas.fifith.alcohol.R
import com.doubleslas.fifith.alcohol.databinding.ActivityMainBinding
import com.doubleslas.fifith.alcohol.databinding.ActivityRegister2Binding
import com.doubleslas.fifith.alcohol.databinding.ActivityRegisterBinding
import com.doubleslas.fifith.alcohol.model.network.base.ApiStatus
import com.doubleslas.fifith.alcohol.ui.auth.recommendinfo.RecommendInfoActivity
import com.doubleslas.fifith.alcohol.ui.detail.AlcoholDetailActivity
import com.doubleslas.fifith.alcohol.ui.main.MainActivity
import com.doubleslas.fifith.alcohol.viewmodel.RegisterViewModel

class Register2Activity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {
    private val registerViewModel by lazy { RegisterViewModel() }
    private lateinit var activityRegister2Binding: ActivityRegister2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRegister2Binding = ActivityRegister2Binding.inflate(layoutInflater)
        setContentView(activityRegister2Binding.root)
        val intent = intent
        activityRegister2Binding.seekbarDrinkCapacity.seekBar.setOnSeekBarChangeListener(this)
        activityRegister2Binding.btnEndRegister2.isEnabled = false

        activityRegister2Binding.seekbarDrinkCapacity.tvLabel1.text = "없음"
        activityRegister2Binding.seekbarDrinkCapacity.tvLabel2.text = "심함"


        val nickname = intent.getStringExtra("nickname").toString()
        val drinkCapacity = arrayOf(
            "0",
            "0.5",
            "1",
            "1.5",
            "2",
            "2.5",
            "3",
            "3.5",
            "4",
            "4.5",
            "5",
            "5.5",
            "6",
            "6.5",
            "7",
            "7.5",
            "8",
            "8.5",
            "9",
            "9.5",
            "10"
        )

        activityRegister2Binding.numberPicker.minValue = 0
        activityRegister2Binding.numberPicker.maxValue = (drinkCapacity.size - 1)
        activityRegister2Binding.numberPicker.displayedValues = drinkCapacity

        activityRegister2Binding.etDrinkingCapacity.setOnClickListener {
            activityRegister2Binding.btnEndRegister2.visibility = View.GONE
            activityRegister2Binding.numberPicker.wrapSelectorWheel = false
            activityRegister2Binding.layoutNumberPicker.visibility = View.VISIBLE
            activityRegister2Binding.btnSetCapacity.setOnClickListener {
                activityRegister2Binding.etDrinkingCapacity.setText((activityRegister2Binding.numberPicker.value.toFloat() / 2).toString())
                drink = activityRegister2Binding.etDrinkingCapacity.text.toString().toFloat()
                activityRegister2Binding.layoutNumberPicker.visibility = View.GONE
                activityRegister2Binding.btnEndRegister2.visibility = View.VISIBLE
                activityRegister2Binding.btnEndRegister2.isEnabled = true

            }


        }

        activityRegister2Binding.btnEndRegister2.setOnClickListener {
            // 닉네임 , 주량 및 숙취 데이터 뷰모델에 전송.

            registerViewModel.register(nickname, drink, hangover).observe(this, Observer {
                when (it) {
                    is ApiStatus.Loading -> {
                    }
                    is ApiStatus.Success -> {
                        App.prefs.registerUserInfo = true
                        val intent = Intent(this, RecommendInfoActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    is ApiStatus.Error -> {
                    }
                }
            })

        }


    }


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        hangover = progress.toFloat()
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {

    }


    companion object {
        var drink: Float = 0.0f
        var hangover: Float = 0.0f
    }

}