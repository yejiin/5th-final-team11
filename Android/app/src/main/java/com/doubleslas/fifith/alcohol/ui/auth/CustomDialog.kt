package com.doubleslas.fifith.alcohol.ui.auth

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.doubleslas.fifith.alcohol.R
import kotlinx.android.synthetic.main.custom_dialog.*

class CustomDialog(context: Context, val txt: String) : Dialog(context), View.OnClickListener {
    constructor(context: Context, customDialogInterface: CustomDialogInterface) : this(context, "")


    var customDialogInterface: CustomDialogInterface? = null


    init {
        this.customDialogInterface = customDialogInterface
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_dialog)

        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        tv_nicknameCheck.text = txt
        btn_nickname_confirm.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view) {
            btn_nickname_confirm -> {
                if (customDialogInterface == null) dismiss()
                else customDialogInterface!!.onConfirmBtnClicked()
            }
        }
    }
}