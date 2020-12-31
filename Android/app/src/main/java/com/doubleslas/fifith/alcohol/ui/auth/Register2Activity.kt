package com.doubleslas.fifith.alcohol.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import com.doubleslas.fifith.alcohol.databinding.ActivityRegister2Binding

class Register2Activity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {
    private lateinit var activityRegister2Binding: ActivityRegister2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRegister2Binding = ActivityRegister2Binding.inflate(layoutInflater)
        setContentView(activityRegister2Binding.root)

        activityRegister2Binding.sbDrinkingCapacity.setOnSeekBarChangeListener(this)
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }
}