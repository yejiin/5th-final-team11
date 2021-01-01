package com.doubleslas.fifith.alcohol.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import com.doubleslas.fifith.alcohol.databinding.ActivityRegisterBinding
import com.doubleslas.fifith.alcohol.model.network.base.ApiStatus
import com.doubleslas.fifith.alcohol.model.network.dto.RegisterRequestData
import com.doubleslas.fifith.alcohol.viewmodel.RegisterViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterActivity : AppCompatActivity() {
    private lateinit var activityRegisterBinding: ActivityRegisterBinding
    private val registerViewModel by lazy { RegisterViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRegisterBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(activityRegisterBinding.root)

        var retrofit = Retrofit.Builder()
            .baseUrl("http://double-slash.shop/user/register/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        activityRegisterBinding.btnEndRegister1.isEnabled = false



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
            val nickname = activityRegisterBinding.etNickname.text.toString()
            registerViewModel.register(nickname).observe(this, Observer {
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


