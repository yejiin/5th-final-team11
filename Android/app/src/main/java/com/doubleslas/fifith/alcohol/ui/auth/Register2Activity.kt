package com.doubleslas.fifith.alcohol.ui.auth

import android.app.AlertDialog
import android.os.Bundle
import android.widget.NumberPicker
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.doubleslas.fifith.alcohol.R
import com.doubleslas.fifith.alcohol.databinding.ActivityRegister2Binding
import com.doubleslas.fifith.alcohol.databinding.ActivityRegisterBinding
import com.doubleslas.fifith.alcohol.model.network.base.ApiStatus
import com.doubleslas.fifith.alcohol.utils.LogUtil
import com.doubleslas.fifith.alcohol.viewmodel.RegisterViewModel

class Register2Activity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {
    private val registerViewModel by lazy { RegisterViewModel() }
    private lateinit var activityRegisterBinding: ActivityRegisterBinding
    private lateinit var activityRegister2Binding: ActivityRegister2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent
        // 닉네임 가져오기
        val nickname = intent.getStringExtra("nickname").toString()
        val drink = activityRegister2Binding.sbDrinkingCapacity.progress.toFloat()
        val hangover: Float = activityRegister2Binding.etDrinkingCapacity.text.toString().toFloat()

        activityRegister2Binding = ActivityRegister2Binding.inflate(layoutInflater)
        setContentView(activityRegister2Binding.root)

        activityRegister2Binding.sbDrinkingCapacity.setOnSeekBarChangeListener(this)

        activityRegister2Binding.etDrinkingCapacity.setOnClickListener {
//            window
//                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
            showDialog()


        }

        activityRegister2Binding.btnEndRegister2.setOnClickListener {
            // 닉네임 , 주량 및 숙취 데이터 뷰모델에 전송.

            registerViewModel.register(nickname, drink, hangover).observe(this, Observer {
                when (it) {
                    is ApiStatus.Loading -> {
                    }
                    is ApiStatus.Success -> {
                        it.data
                    }
                    is ApiStatus.Error -> {
                    }
                }
            })

        }


    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        LogUtil.d("capacity", "progress: $progress")

    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }

    private fun showDialog() {


        val dialog = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.number_picker, null)
        val numberPicker = dialogView.findViewById<NumberPicker>(R.id.numberPicker)

        dialog.setTitle("주량을 입력하세요")
        dialog.setMessage("주량")
        dialog.setView(dialogView)
        numberPicker.minValue = 0
        numberPicker.maxValue = 10
        numberPicker.wrapSelectorWheel = false
        numberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            LogUtil.d("numberPicker", "current Value: $newVal")
        }
        dialog.setPositiveButton("완료") { dialog, which ->
            activityRegister2Binding.etDrinkingCapacity.setText(numberPicker.value.toString())
        }

        val alertDialog = dialog.create()
        alertDialog.show()


    }


}