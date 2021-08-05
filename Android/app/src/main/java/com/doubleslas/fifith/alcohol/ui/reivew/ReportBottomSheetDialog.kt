package com.doubleslas.fifith.alcohol.ui.reivew

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import com.doubleslas.fifith.alcohol.databinding.LayoutReportReviewBinding
import com.doubleslas.fifith.alcohol.ui.common.base.BaseBottomSheetDialogFragment

class ReportBottomSheetDialog : BaseBottomSheetDialogFragment<LayoutReportReviewBinding>(),
    CompoundButton.OnCheckedChangeListener {
    private var listener: ((String) -> Unit)? = null

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): LayoutReportReviewBinding {
        return LayoutReportReviewBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.let { b ->
            b.btnReportConfirm.setOnClickListener {
                val comment =
                    if (b.cbReport1.isChecked) {
                        "홍보"
                    } else if (b.cbReport2.isChecked) {
                        "음란/ 부적절"
                    } else if (b.cbReport3.isChecked) {
                        "명예 훼손 / 사행활 침해"
                    } else if (b.cbReport4.isChecked) {
                        val comment = b.etReportContent.text
                        if (comment.length < 10) {
                            Toast.makeText(context, "기타 의견은 10자 이상 작성해야합니다", Toast.LENGTH_SHORT)
                                .show()
                            return@setOnClickListener
                        } else {
                            b.etReportContent.text.toString()
                        }
                    } else {
                        return@setOnClickListener
                    }
                listener?.invoke(comment)
                dismiss()
            }

            b.btnClose.setOnClickListener {
                dismiss()
            }

            b.cbReport1.setOnCheckedChangeListener(this)
            b.cbReport2.setOnCheckedChangeListener(this)
            b.cbReport3.setOnCheckedChangeListener(this)
            b.cbReport4.setOnCheckedChangeListener(this)
        }
    }

    fun setListener(listener: ((String) -> Unit)) {
        this.listener = listener
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        binding?.btnReportConfirm?.isEnabled = true
    }
}