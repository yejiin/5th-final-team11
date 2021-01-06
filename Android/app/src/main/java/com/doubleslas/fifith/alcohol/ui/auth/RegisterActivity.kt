package com.doubleslas.fifith.alcohol.ui.auth

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.doubleslas.fifith.alcohol.databinding.ActivityRegisterBinding
import com.doubleslas.fifith.alcohol.model.network.base.ApiStatus
import com.doubleslas.fifith.alcohol.utils.LogUtil
import com.doubleslas.fifith.alcohol.viewmodel.RegisterViewModel
import kotlinx.android.synthetic.main.custom_dialog.*

class RegisterActivity : AppCompatActivity(), CustomDialogInterface {

    private val customDialog: CustomDialog by lazy { CustomDialog(this, this) }
    private lateinit var activityRegisterBinding: ActivityRegisterBinding
    private val registerViewModel by lazy { RegisterViewModel() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRegisterBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(activityRegisterBinding.root)


//        activityRegisterBinding.btnEndRegister1.isEnabled = false


        activityRegisterBinding.cbAdmitAll.setOnClickListener {
            checkAll()
        }

        activityRegisterBinding.btnValidate.setOnClickListener {
            nickname = activityRegisterBinding.etNickname.text.toString()
            // 닉네임 중복 체크
            registerViewModel.nicknameCheck(nickname).observe(this, Observer {
                when (it) {
                    is ApiStatus.Loading -> {
                    }
                    is ApiStatus.Success -> {
                        it.data
                        onDialogBtnClicked("해당 닉네임은\n 사용 가능합니다.")
                    }
                    is ApiStatus.Error -> {
                        onDialogBtnClicked("해당 닉네임은\n 이미 사용 중입니다.")
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

    private fun checkAll() {
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

    private fun checkEssential() {
        if (activityRegisterBinding.cbEssential1.isChecked && activityRegisterBinding.cbEssential2.isChecked && activityRegisterBinding.cbEssential3.isChecked) {
            activityRegisterBinding.btnEndRegister1.isEnabled = true
            activityRegisterBinding.btnEndRegister1.setTextColor(Color.parseColor("#FFFFFF"))
            activityRegisterBinding.btnEndRegister1.setBackgroundColor(Color.parseColor("#4638CE"))
        }
    }

    private fun onDialogBtnClicked(text: String?) {
        customDialog.show()
        customDialog.tv_nicknameCheck?.text = text

    }


    override fun onConfirmBtnClicked() {
        LogUtil.d("confirm", "button clicked")
        customDialog.dismiss()
    }

    companion object {
        var nickname: String = ""
    }


}


