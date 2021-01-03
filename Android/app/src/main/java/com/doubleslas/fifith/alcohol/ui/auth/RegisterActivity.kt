package com.doubleslas.fifith.alcohol.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.doubleslas.fifith.alcohol.databinding.ActivityRegisterBinding
import com.doubleslas.fifith.alcohol.model.network.base.ApiStatus
import com.doubleslas.fifith.alcohol.viewmodel.RegisterViewModel

class RegisterActivity : AppCompatActivity() {


    private lateinit var activityRegisterBinding: ActivityRegisterBinding
    private val registerViewModel by lazy { RegisterViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRegisterBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(activityRegisterBinding.root)


        val nickname = activityRegisterBinding.etNickname.text.toString()





//        activityRegisterBinding.btnEndRegister1.isEnabled = false



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
            // 닉네임 중복 체크
            registerViewModel.nicknameCheck(nickname).observe(this, Observer {
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

        activityRegisterBinding.btnEndRegister1.setOnClickListener {

            val intent = Intent(this, Register2Activity::class.java)
            intent.putExtra("nickname", nickname)
            startActivity(intent)
        }
    }


}


//    fun onCheckboxClicked(view: View) {
//        if (view is CheckBox) {
//            if (activityRegisterBinding.cbEssential1.isChecked && activityRegisterBinding.cbEssential2.isChecked && activityRegisterBinding.cbEssential3.isChecked) {
//                activityRegisterBinding.btnEndRegister.isEnabled = true
//                activityRegisterBinding.btnEndRegister.setTextColor(Color.parseColor("#FFFFFF"))
//            } else {
//                activityRegisterBinding.btnEndRegister.isEnabled = false
//                activityRegisterBinding.btnEndRegister.setTextColor(Color.parseColor("#575757"))
//            }
//        }
//    }


