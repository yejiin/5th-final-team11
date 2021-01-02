package com.doubleslas.fifith.alcohol.ui.auth

import android.app.AlertDialog
import android.os.Bundle
import android.widget.NumberPicker
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.doubleslas.fifith.alcohol.R
import com.doubleslas.fifith.alcohol.databinding.ActivityRegister2Binding
import com.doubleslas.fifith.alcohol.utils.LogUtil
import com.doubleslas.fifith.alcohol.viewmodel.RegisterViewModel

class Register2Activity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {
    private val registerViewModel by lazy { RegisterViewModel() }
    private lateinit var activityRegister2Binding: ActivityRegister2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRegister2Binding = ActivityRegister2Binding.inflate(layoutInflater)
        setContentView(activityRegister2Binding.root)

        activityRegister2Binding.sbDrinkingCapacity.setOnSeekBarChangeListener(this)

        activityRegister2Binding.etDrinkingCapacity.setOnClickListener {
//            window
//                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
            showDialog()


        }

        activityRegister2Binding.btnEndRegister2.setOnClickListener {
            //  주량 및 숙취 데이터 뷰모델에 전송.

        }


    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        val drinkCapacity = seekBar?.progress

    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }

    private fun showDialog() {
//        val numberPicker: NumberPicker = NumberPicker(this)
//        numberPicker.minValue = 0
//        numberPicker.maxValue = 10
//        val builder = AlertDialog.Builder(this)
//        builder.setView(numberPicker)
//        builder.setTitle("주량을 입력하세요")
//        builder.setMessage("주량")
//
//        builder.create()
//        return builder.show()

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
//        dialog.setPositiveButton("Done") { dialog, which ->
//            LogUtil.d("onClick: ", numberPicker.value.toString())
//        }
//        dialog.setNegativeButton("Cancel") { dialog, which ->
//        }
        val alertDialog = dialog.create()
        alertDialog.show()

    }


}