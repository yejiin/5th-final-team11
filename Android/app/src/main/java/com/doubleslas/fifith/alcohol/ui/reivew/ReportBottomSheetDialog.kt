package com.doubleslas.fifith.alcohol.ui.reivew

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.doubleslas.fifith.alcohol.databinding.LayoutReportReviewBinding
import com.doubleslas.fifith.alcohol.model.network.base.ApiStatus
import com.doubleslas.fifith.alcohol.ui.auth.CustomDialog
import com.doubleslas.fifith.alcohol.ui.auth.CustomDialogInterface
import com.doubleslas.fifith.alcohol.ui.common.base.BaseBottomSheetDialogFragment
import com.doubleslas.fifith.alcohol.viewmodel.ReviewViewModel
import kotlinx.android.synthetic.main.custom_dialog.*

class ReportBottomSheetDialog : BaseBottomSheetDialogFragment<LayoutReportReviewBinding>(), CustomDialogInterface {

    private val customDialog: CustomDialog by lazy { CustomDialog(context!!, this) }

    private val reviewViewModel by lazy { ReviewViewModel() }
    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): LayoutReportReviewBinding {
        val view = LayoutReportReviewBinding.inflate(inflater, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.let { b ->
            b.btnReportConfirm.setOnClickListener {
                confirmReport()
            }
        }
    }


    private fun confirmReport() {
        binding?.let { b ->

            val content = b.etReportContent.text.toString()
            val liveData = reviewViewModel.reportReview(6, content)

            liveData.observe(viewLifecycleOwner, Observer {
                when (it) {
                    is ApiStatus.Loading -> {

                    }
                    is ApiStatus.Success -> {
                        onDialogBtnClicked("신고가\n접수되었습니다.")
                    }
                    is ApiStatus.Error -> {

                    }
                }
            })

        }
    }

    private fun onDialogBtnClicked(text: String?) {
        customDialog.show()
        customDialog.tv_nicknameCheck?.text = text

    }

    override fun onConfirmBtnClicked() {
        customDialog.dismiss()
    }

}