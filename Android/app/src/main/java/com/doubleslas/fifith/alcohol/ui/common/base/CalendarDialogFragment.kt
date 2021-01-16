package com.doubleslas.fifith.alcohol.ui.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.doubleslas.fifith.alcohol.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_calendar_dialog.*
import java.util.*


class CalendarDialogFragment : BottomSheetDialogFragment() {
    val calendar: Calendar = Calendar.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        super.onCreateView(inflater, container, savedInstanceState)

        val view = inflater.inflate(R.layout.fragment_calendar_dialog, container, false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_calendar_confirm.setOnClickListener {

            val year = date_picker_calendar.year.toString()
            val month = date_picker_calendar.month.toString()
            val day = date_picker_calendar.dayOfMonth.toString()

            val date = year + "." + month + "." + day

            val reviewBottomSheetDialog = ReviewBottomSheetDialog()
            val view = reviewBottomSheetDialog.view
            if (view != null) {
                view.findViewById<EditText>(R.id.et_calendar).setText(date)
            }



            dismiss()
        }

    }

    companion object {
        var date = ""
    }

}
