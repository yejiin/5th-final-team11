package com.doubleslas.fifith.alcohol.ui.reivew

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.doubleslas.fifith.alcohol.R
import com.doubleslas.fifith.alcohol.databinding.LayoutWriteReviewBinding
import com.doubleslas.fifith.alcohol.model.network.base.ApiStatus
import com.doubleslas.fifith.alcohol.model.network.dto.ReviewDetailData
import com.doubleslas.fifith.alcohol.ui.common.CalendarDialogFragment
import com.doubleslas.fifith.alcohol.ui.common.base.BaseBottomSheetDialogFragment
import com.doubleslas.fifith.alcohol.viewmodel.ReviewViewModel


class ReviewBottomSheetDialog : BaseBottomSheetDialogFragment<LayoutWriteReviewBinding>() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(ReviewViewModel::class.java)
    }

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
                confirmReview()
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

    private fun confirmReview() {
        binding?.let { b ->
            val detail = ReviewDetailData(
                b.etComment.text.toString(),
                b.etDrink.text.toString().toInt(),
                b.seekBarHangover.seekBar.progress,
                b.etPlace.text.toString(),
                b.etPrice.text.toString().toInt(),
                b.checkboxPrivate.isChecked
            )

            val liveData = viewModel.sendReview(
                b.etComment.text.toString(),
                b.ratingReview.progress,
                detail
            )

            liveData.observe(viewLifecycleOwner, Observer {
                when (it) {
                    is ApiStatus.Loading -> {

                    }
                    is ApiStatus.Success -> {
                        dismiss()
                    }
                    is ApiStatus.Error -> {
                        processError(it.code)
                    }
                }
            })
        }
    }

    private fun processError(code: Int) {
        when (code) {
            ReviewViewModel.ErrorCode.COMMENT.ordinal -> {
                binding?.etComment?.requestFocus()
            }
            ReviewViewModel.ErrorCode.DETAIL_DATE.ordinal -> {
                binding?.etCalendar?.requestFocus()
            }
            ReviewViewModel.ErrorCode.DETAIL_PLACE.ordinal -> {
                binding?.etPlace?.requestFocus()
            }
            ReviewViewModel.ErrorCode.DETAIL_DRINK.ordinal -> {
                binding?.etDrink?.requestFocus()
            }
            ReviewViewModel.ErrorCode.DETAIL_PRICE.ordinal -> {
                binding?.etPrice?.requestFocus()
            }
        }
    }

}



