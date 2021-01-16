package com.doubleslas.fifith.alcohol.ui.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.doubleslas.fifith.alcohol.R
import kotlinx.android.synthetic.main.fragment_calendar_dialog.*


class CalendarDialogFragment : DialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_calendar_dialog, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



        btn_calendar_confirm.setOnClickListener {
            dismiss()
        }


    }

}
