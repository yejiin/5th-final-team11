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

        et_calendar.setOnClickListener {
            val calendarDialogFragment = CalendarDialogFragment()

            activity?.supportFragmentManager?.let { it1 ->
                calendarDialogFragment.show(
                    it1,
                    calendarDialogFragment.tag
                )
            }


        }

        btn_review_confirm.setOnClickListener {
            dismiss()
        }



        cl_detail_record.setOnClickListener {
            if (cl_review_detail.visibility == View.GONE) {
                iv__detail_record.setImageResource(R.drawable.ic_review_button_x)
                cl_review_detail.visibility = View.VISIBLE


            } else {
                iv__detail_record.setImageResource(R.drawable.ic_review_button_plus)
                cl_review_detail.visibility = View.GONE

            }
        }


    }


}
