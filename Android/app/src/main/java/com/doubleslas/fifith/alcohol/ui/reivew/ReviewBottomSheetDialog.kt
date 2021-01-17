package com.doubleslas.fifith.alcohol.ui.reivew

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.doubleslas.fifith.alcohol.R
import com.doubleslas.fifith.alcohol.databinding.LayoutWriteReviewBinding
import com.doubleslas.fifith.alcohol.ui.common.CalendarDialogFragment
import com.doubleslas.fifith.alcohol.ui.common.base.BaseBottomSheetDialogFragment


class ReviewBottomSheetDialog : BaseBottomSheetDialogFragment<LayoutWriteReviewBinding>() {
    private val calendarDialogFragment by lazy {
        CalendarDialogFragment().apply {
            setOnConfirmListener { year, month, day ->
                val txt = "${year}.${month}.${day}"
                binding?.etCalendar?.setText(txt)
            }
        }
    }


    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): LayoutWriteReviewBinding {
        return LayoutWriteReviewBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.let { b ->
            b.seekBarHangover.tvLabel1.text = getString(R.string.hangover_none)
            b.seekBarHangover.tvLabel2.text = getString(R.string.hangover_heavy)

            b.ivDetailRecord.setImageResource(R.drawable.ic_review_button_plus)
            b.layoutDetailReview.visibility = View.GONE

            b.etCalendar.setOnClickListener {
                activity!!.supportFragmentManager.let { fm ->
                    calendarDialogFragment.show(fm, null)
                }
            }


            b.btnReviewConfirm.setOnClickListener {
                // TODO: 서버 전송
                dismiss()
            }

            b.layoutDetailToggle.setOnClickListener {
                if (b.layoutDetailReview.visibility == View.GONE) {
                    b.ivDetailRecord.setImageResource(R.drawable.ic_review_button_x)
                    b.layoutDetailReview.visibility = View.VISIBLE
                } else {
                    b.ivDetailRecord.setImageResource(R.drawable.ic_review_button_plus)
                    b.layoutDetailReview.visibility = View.GONE
                }
            }
        }
    }


}



