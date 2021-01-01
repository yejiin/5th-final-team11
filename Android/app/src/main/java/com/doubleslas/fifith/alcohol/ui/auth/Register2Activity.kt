package com.doubleslas.fifith.alcohol.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import com.doubleslas.fifith.alcohol.databinding.ActivityRegister2Binding
import kotlinx.android.synthetic.main.activity_register2.*

class Register2Activity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {
    private lateinit var activityRegister2Binding: ActivityRegister2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRegister2Binding = ActivityRegister2Binding.inflate(layoutInflater)
        setContentView(activityRegister2Binding.root)

        activityRegister2Binding.sbDrinkingCapacity.setOnSeekBarChangeListener(this)

        activityRegister2Binding.btnEndRegister2.setOnClickListener {
            // 주량 및 숙취 데이터 뷰모델에 전송.
        }


    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        val drinkCapacity = seekBar?.progress

    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }


}