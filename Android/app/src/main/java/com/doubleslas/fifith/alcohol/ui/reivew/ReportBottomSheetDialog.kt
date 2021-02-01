package com.doubleslas.fifith.alcohol.ui.reivew

import android.view.LayoutInflater
import android.view.ViewGroup
import com.doubleslas.fifith.alcohol.databinding.LayoutReportReviewBinding
import com.doubleslas.fifith.alcohol.ui.common.base.BaseBottomSheetDialogFragment

class ReportBottomSheetDialog: BaseBottomSheetDialogFragment<LayoutReportReviewBinding>() {

    private val reviewViewModel by lazy { ReviewViewModel() }
    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): LayoutReportReviewBinding {
        val view = LayoutReportReviewBinding.inflate(inflater, container, false)
        return view
    }
}