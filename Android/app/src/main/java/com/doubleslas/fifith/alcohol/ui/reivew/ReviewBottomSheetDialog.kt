package com.doubleslas.fifith.alcohol.ui.reivew

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.doubleslas.fifith.alcohol.R
import com.doubleslas.fifith.alcohol.databinding.LayoutWriteReviewBinding
import com.doubleslas.fifith.alcohol.model.base.ApiStatus
import com.doubleslas.fifith.alcohol.dto.ReviewDetailData
import com.doubleslas.fifith.alcohol.ui.auth.CustomDialog
import com.doubleslas.fifith.alcohol.ui.auth.CustomDialogInterface
import com.doubleslas.fifith.alcohol.ui.common.CalendarDialogFragment
import com.doubleslas.fifith.alcohol.ui.common.base.BaseBottomSheetDialogFragment
import kotlinx.android.synthetic.main.custom_dialog.*


class ReviewBottomSheetDialog private constructor() :
    BaseBottomSheetDialogFragment<LayoutWriteReviewBinding>(), CustomDialogInterface {

    private val alcoholId by lazy { arguments!!.getInt(ARGUMENT_ALCOHOL_ID) }
    private val customDialog: CustomDialog by lazy { CustomDialog(context!!, this) }


    private val viewModel by lazy {
        ReviewViewModel()
    }

    private val calendarDialogFragment by lazy {
        CalendarDialogFragment().apply {
            setOnConfirmListener { year, month, day ->
                val txt = "${year}.${month}.${day}"
                binding?.layoutDetail?.etCalendar?.setText(txt)
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
            b.layoutDetail.seekBarHangover.tvLabel1.text = getString(R.string.hangover_none)
            b.layoutDetail.seekBarHangover.tvLabel2.text = getString(R.string.hangover_heavy)

            b.ivDetailRecord.setImageResource(R.drawable.ic_review_button_plus)
            b.layoutDetail.layoutDetailReview.visibility = View.GONE

            b.layoutDetail.etCalendar.setOnClickListener {
                activity!!.supportFragmentManager.let { fm ->
                    calendarDialogFragment.show(fm, null)
                }
            }


            b.btnReviewConfirm.setOnClickListener {

                confirmReview()

            }

            b.layoutDetailToggle.setOnClickListener {
                if (b.layoutDetail.layoutDetailReview.visibility == View.GONE) {
                    b.ivDetailRecord.setImageResource(R.drawable.ic_review_button_x)
                    b.layoutDetail.layoutDetailReview.visibility = View.VISIBLE
                    b.checkboxPrivate.visibility = View.VISIBLE
                } else {
                    b.ivDetailRecord.setImageResource(R.drawable.ic_review_button_plus)
                    b.layoutDetail.layoutDetailReview.visibility = View.GONE
                    b.checkboxPrivate.visibility = View.INVISIBLE
                }
            }
        }
    }

    private fun confirmReview() {
        binding?.let { b ->


            val detail =
                if (b.layoutDetail.root.isVisible)
                    ReviewDetailData(
                        b.layoutDetail.etCalendar.text.toString(),
                        b.layoutDetail.etDrink.text.toString().toInt(),
                        b.layoutDetail.seekBarHangover.seekBar.progress,
                        b.layoutDetail.etPlace.text.toString(),
                        b.layoutDetail.etPrice.text.toString().toInt(),
                        b.checkboxPrivate.isChecked
                    )
                else
                    null

            val liveData = viewModel.sendReview(
                b.etComment.text.toString(),
                b.ratingReview.progress,
                detail,
                alcoholId
            )

            liveData.observe(viewLifecycleOwner, Observer {
                when (it) {
                    is ApiStatus.Loading -> {

                    }
                    is ApiStatus.Success -> {
                        onDialogBtnClicked("리뷰를 작성해주셔서\n감사합니다.")
                        listener?.invoke()
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
                binding?.layoutDetail?.etCalendar?.requestFocus()
            }
            ReviewViewModel.ErrorCode.DETAIL_PLACE.ordinal -> {
                binding?.layoutDetail?.etPlace?.requestFocus()
            }
            ReviewViewModel.ErrorCode.DETAIL_DRINK.ordinal -> {
                binding?.layoutDetail?.etDrink?.requestFocus()
            }
            ReviewViewModel.ErrorCode.DETAIL_PRICE.ordinal -> {
                binding?.layoutDetail?.etPrice?.requestFocus()
            }
        }
    }

    override fun onConfirmBtnClicked() {
        customDialog.dismiss()
    }

    private var listener: (() -> Unit)? = null
    fun onListener(listener: () -> Unit) {
        this.listener = listener
    }

    private fun onDialogBtnClicked(text: String?) {
        customDialog.show()
        customDialog.tv_nicknameCheck?.text = text
//        customDialog.customDialogInterface = object : CustomDialogInterface {
//            override fun onConfirmBtnClicked() {
//                customDialog.dismiss()
//                dismiss()
//            }
//        }
    }

    companion object {
        private const val ARGUMENT_ALCOHOL_ID = "ARGUMENT_ALCOHOL_ID"
        fun create(id: Int): ReviewBottomSheetDialog {
            return ReviewBottomSheetDialog().apply {
                arguments = Bundle().apply {
                    putInt(ARGUMENT_ALCOHOL_ID, id)
                }
            }
        }
    }

}



