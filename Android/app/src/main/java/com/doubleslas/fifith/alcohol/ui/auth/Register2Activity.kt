package com.doubleslas.fifith.alcohol.ui.auth

import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
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



        activityRegister2Binding = ActivityRegister2Binding.inflate(layoutInflater)
        setContentView(activityRegister2Binding.root)
        val intent = intent
        // 닉네임 가져오기

        activityRegister2Binding.sbDrinkingCapacity.setOnSeekBarChangeListener(this)

        val nickname = intent.getStringExtra("nickname").toString()

        activityRegister2Binding.etDrinkingCapacity.setOnClickListener {
            activityRegister2Binding.btnEndRegister2.visibility = View.GONE
            activityRegister2Binding.numberPicker.minValue = 0
            activityRegister2Binding.numberPicker.maxValue = 10
            activityRegister2Binding.numberPicker.wrapSelectorWheel = false
            activityRegister2Binding.layoutNumberPicker.visibility = View.VISIBLE
            activityRegister2Binding.btnSetCapacity.setOnClickListener {
                activityRegister2Binding.etDrinkingCapacity.setText(activityRegister2Binding.numberPicker.value.toString())
                drink = activityRegister2Binding.etDrinkingCapacity.text.toString().toFloat()
                activityRegister2Binding.layoutNumberPicker.visibility = View.GONE
                activityRegister2Binding.btnEndRegister2.visibility = View.VISIBLE
            }


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
        hangover = progress.toFloat()

    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }

//    private fun showDialog() {
//
//
//        val dialog = AlertDialog.Builder(this)
//        val inflater = this.layoutInflater
//        val dialogView = inflater.inflate(R.layout.number_picker, null)
//        val numberPicker = dialogView.findViewById<NumberPicker>(R.id.numberPicker)
//
//        dialog.setTitle("주량을 입력하세요")
//        dialog.setMessage("주량")
//        dialog.setView(dialogView)
//        numberPicker.minValue = 0
//        numberPicker.maxValue = 10
//        numberPicker.wrapSelectorWheel = false
//        numberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
//            LogUtil.d("numberPicker", "current Value: $newVal")
//        }
//        dialog.setPositiveButton("완료") { dialog, which ->
//            activityRegister2Binding.etDrinkingCapacity.setText(numberPicker.value.toString())
//            drink = activityRegister2Binding.etDrinkingCapacity.text.toString().toFloat()
//        }
//
//        val alertDialog = dialog.create()
//        alertDialog.show()
//
//
//    }

    companion object {
        var drink: Float = 0.0f
        var hangover: Float = 0.0f
    }

}