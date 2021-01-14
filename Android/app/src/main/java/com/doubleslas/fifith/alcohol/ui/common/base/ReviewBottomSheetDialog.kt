package com.doubleslas.fifith.alcohol.ui.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.doubleslas.fifith.alcohol.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.layout_write_review.*


class ReviewBottomSheetDialog : BottomSheetDialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.layout_write_review, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btn_review_confirm.setOnClickListener {
            dismiss()
        }
        btn_review_detail.setOnClickListener {
            if (cl_review_detail.visibility == View.GONE) {
                cl_review_detail.visibility = View.VISIBLE
                btn_review_detail.text = "상세기록 끄기"
            } else {
                cl_review_detail.visibility = View.GONE
                btn_review_detail.text = "상세기록"
            }
        }
    }

}
