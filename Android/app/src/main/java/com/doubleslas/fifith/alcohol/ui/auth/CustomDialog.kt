package com.doubleslas.fifith.alcohol.ui.auth

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import com.doubleslas.fifith.alcohol.R
import kotlinx.android.synthetic.main.custom_dialog.*

class CustomDialog(context: Context, customDialogInterface: CustomDialogInterface) : Dialog(context), View.OnClickListener {

    var customDialogInterface: CustomDialogInterface? = null

    init {
        this.customDialogInterface = customDialogInterface
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_dialog)

        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        btn_nickname_confirm.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view) {
            btn_nickname_confirm -> {
                this.customDialogInterface?.onConfirmBtnClicked()

            }
        }
    }
}