package com.doubleslas.fifith.alcohol.ui.auth

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.doubleslas.fifith.alcohol.R
import com.doubleslas.fifith.alcohol.databinding.ActivityRegisterBinding
import com.doubleslas.fifith.alcohol.model.base.ApiStatus
import kotlinx.android.synthetic.main.custom_dialog.*

class RegisterActivity : AppCompatActivity(), CustomDialogInterface, View.OnClickListener {
    private val activeButtonDrawable by lazy {
        AppCompatResources.getDrawable(
            this,
            R.drawable.button_background_gradient
        )
    }
    private val customDialog: CustomDialog by lazy { CustomDialog(this, this) }
    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel by lazy { RegisterViewModel() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnEndRegister1.isEnabled = false
        binding.cbAll.setOnClickListener {
            checkAll()
            isChecked()
        }

        binding.cbAge.setOnClickListener(this)
        binding.cbTerms.setOnClickListener(this)
        binding.cbPrivacy.setOnClickListener(this)
        binding.cbMarketing.setOnClickListener(this)

        binding.btnValidate.setOnClickListener {
            val nickname = binding.etNickname.text.toString()
            if (nickname.length < 2 || nickname.length > 10) {
                Toast.makeText(this, "2자 이상, 10자 이하로 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 닉네임 중복 체크
            registerViewModel.nicknameCheck(nickname).observe(this, Observer {
                when (it) {
                    is ApiStatus.Success -> {
                        it.data
                        onDialogBtnClicked("해당 닉네임은\n 사용 가능합니다.")
                        refreshRegisterButton()
                    }
                    is ApiStatus.Error -> {
                        onDialogBtnClicked("해당 닉네임은\n 이미 사용 중입니다.")
                        refreshRegisterButton()
                    }
                }
            })
        }

        binding.btnEndRegister1.setOnClickListener {

            if (registerViewModel.nickname != binding.etNickname.text.toString()) {
                onDialogBtnClicked("중복 확인을\n 해주세요!")
            }
            if (registerViewModel.nickname == binding.etNickname.text.toString()) {
                val intent = Intent(this, Register2Activity::class.java)
                intent.putExtra("registerViewModel.nickname", registerViewModel.nickname)
                startActivity(intent)
                finish()
            }
        }

        binding.etNickname.addTextChangedListener {
            val nickname = it.toString()
            if (nickname.length < 2 || nickname.length > 10) {
                binding.tvNicknameWaring.text = "2자 이상, 10자 이하로 입력해주세요."
            } else {
                binding.tvNicknameWaring.text = ""
            }
        }
    }


    private fun checkAll() {
        if (binding.cbAll.isChecked) {
            binding.cbAge.isChecked = true
            binding.cbTerms.isChecked = true
            binding.cbPrivacy.isChecked = true
            binding.cbMarketing.isChecked = true
        } else {
            binding.cbAge.isChecked = false
            binding.cbTerms.isChecked = false
            binding.cbPrivacy.isChecked = false
            binding.cbMarketing.isChecked = false
        }
    }

    private fun isChecked() {
        val essentialCheck = isEssentialAllCheck()

        if (binding.cbAll.isChecked) {
            binding.cbAll.isChecked = false
        }

        if (essentialCheck && binding.cbMarketing.isChecked) {
            binding.cbAll.isChecked = true
        } else if (essentialCheck && registerViewModel.nickname != "") {
            binding.btnEndRegister1.isEnabled = true
        }

        refreshRegisterButton()
    }

    private fun refreshRegisterButton() {
        if (isEssentialAllCheck() && registerViewModel.nickname != null) {
            binding.btnEndRegister1.setBackgroundDrawable(activeButtonDrawable)
            binding.btnEndRegister1.setTextColor(Color.parseColor("#FFFFFF"))
            binding.btnEndRegister1.isEnabled = true
        } else {
            binding.btnEndRegister1.setBackgroundColor(Color.parseColor("#202425"))
            binding.btnEndRegister1.setTextColor(Color.parseColor("#575757"))
            binding.btnEndRegister1.isEnabled = false
        }
    }

    private fun isEssentialAllCheck(): Boolean {
        return binding.cbAge.isChecked && binding.cbPrivacy.isChecked && binding.cbTerms.isChecked
    }


    private fun onDialogBtnClicked(text: String?) {
        customDialog.show()
        customDialog.tv_nicknameCheck?.text = text
    }


    override fun onConfirmBtnClicked() {
        customDialog.dismiss()
    }

    override fun onClick(v: View) {
        isChecked()
    }

}


