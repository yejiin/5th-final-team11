package com.doubleslas.fifith.alcohol.ui.auth

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import com.doubleslas.fifith.alcohol.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var activityRegisterBinding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRegisterBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(activityRegisterBinding.root)

        activityRegisterBinding.btnEndRegister.isEnabled = false



        activityRegisterBinding.cbAdmitAll.setOnClickListener {

            if (activityRegisterBinding.cbAdmitAll.isChecked) {
                activityRegisterBinding.cbEssential1.isChecked = true
                activityRegisterBinding.cbEssential2.isChecked = true
                activityRegisterBinding.cbEssential3.isChecked = true
                activityRegisterBinding.cbChoice1.isChecked = true
            } else {
                activityRegisterBinding.cbEssential1.isChecked = false
                activityRegisterBinding.cbEssential2.isChecked = false
                activityRegisterBinding.cbEssential3.isChecked = false
                activityRegisterBinding.cbChoice1.isChecked = false
            }
        }

        activityRegisterBinding.btnValidate.setOnClickListener {
        }


    }

    fun onCheckboxClicked(view: View) {
        if (view is CheckBox) {
            if (activityRegisterBinding.cbEssential1.isChecked && activityRegisterBinding.cbEssential2.isChecked && activityRegisterBinding.cbEssential3.isChecked) {
                activityRegisterBinding.btnEndRegister.isEnabled = true
                activityRegisterBinding.btnEndRegister.setTextColor(Color.parseColor("#FFFFFF"))
            } else {
                activityRegisterBinding.btnEndRegister.isEnabled = false
                activityRegisterBinding.btnEndRegister.setTextColor(Color.parseColor("#575757"))
            }
        }
    }

}