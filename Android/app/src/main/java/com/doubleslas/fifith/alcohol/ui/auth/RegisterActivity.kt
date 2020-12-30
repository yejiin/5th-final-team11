package com.doubleslas.fifith.alcohol.ui.auth

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.doubleslas.fifith.alcohol.databinding.ActivityRegisterBinding
import com.doubleslas.fifith.alcohol.model.network.dto.Register
import com.doubleslas.fifith.alcohol.model.network.dto.RegisterService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterActivity : AppCompatActivity() {
    private lateinit var activityRegisterBinding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRegisterBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(activityRegisterBinding.root)

        var retrofit = Retrofit.Builder()
            .baseUrl("http://double-slash.shop/user/register/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var registerService = retrofit.create(RegisterService::class.java)

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
            val nickname = activityRegisterBinding.etNickname.text.toString()
            registerService.requestRegister(nickname).enqueue(object : Callback<Register> {
                override fun onResponse(call: Call<Register>, response: Response<Register>) {
                    var register = response.body()
                    var dialog = AlertDialog.Builder(this@RegisterActivity)
                    dialog.setMessage("알람")
                    dialog.setMessage("code: " + register?.code)
                    dialog.show()
                }

                override fun onFailure(call: Call<Register>, t: Throwable) {
                    var dialog = AlertDialog.Builder(this@RegisterActivity)
                    dialog.setTitle("실패")
                    dialog.setMessage("통신에 실패함")
                    dialog.show()
                }

            })
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