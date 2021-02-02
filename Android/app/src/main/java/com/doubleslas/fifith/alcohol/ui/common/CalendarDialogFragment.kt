package com.doubleslas.fifith.alcohol.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.doubleslas.fifith.alcohol.R
import com.doubleslas.fifith.alcohol.databinding.FragmentCalendarDialogBinding
import com.doubleslas.fifith.alcohol.ui.common.base.BaseBottomSheetDialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_calendar_dialog.*
import java.util.*


class CalendarDialogFragment : BaseBottomSheetDialogFragment<FragmentCalendarDialogBinding>() {
    private var onConfirmListener: ((Int, Int, Int) -> Unit)? = null

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCalendarDialogBinding {
        return FragmentCalendarDialogBinding.inflate(inflater, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_calendar_confirm.setOnClickListener {

            val year = date_picker_calendar.year
            val month = date_picker_calendar.month + 1
            val day = date_picker_calendar.dayOfMonth

            onConfirmListener?.invoke(year, month, day)
            dismiss()
        }

    }


    fun setOnConfirmListener(listener: ((Int, Int, Int) -> Unit)?) {
        onConfirmListener = listener
    }

}
