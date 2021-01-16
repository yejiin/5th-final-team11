package com.doubleslas.fifith.alcohol.ui.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import com.doubleslas.fifith.alcohol.R
import com.doubleslas.fifith.alcohol.ui.common.base.CalendarDialogFragment.Companion.date

import com.doubleslas.fifith.alcohol.ui.main.MainActivity
import com.doubleslas.fifith.alcohol.utils.LogUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.layout_write_review.*


class ReviewBottomSheetDialog : BottomSheetDialogFragment() {



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.layout_write_review, container, false)
        return view



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        et_calendar.setText(date)

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        val calendarDialogFragment = CalendarDialogFragment()
        super.onActivityCreated(savedInstanceState)



        et_calendar.setOnClickListener {


            activity?.supportFragmentManager?.let { it1 ->
                calendarDialogFragment.show(
                    it1,
                    "Calendar"
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



