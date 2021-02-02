package com.doubleslas.fifith.alcohol.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.doubleslas.fifith.alcohol.databinding.FragmentNumberInputBinding
import com.doubleslas.fifith.alcohol.ui.common.base.BaseBottomSheetDialogFragment

class NumberInputBottomSheetDialog :
    BaseBottomSheetDialogFragment<FragmentNumberInputBinding>() {

    private var listener: ((Int) -> Unit)? = null
    private var min: Int = 0
    private var max: Int = 1
    private var step: Int = 1
    private var value: Int = 0
    private lateinit var list: Array<String>

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentNumberInputBinding {
        return FragmentNumberInputBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.let { b ->
            b.btnConfirm.setOnClickListener {
                listener?.invoke(getValue())
                dismiss()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        processRange()
    }

    override fun show(manager: FragmentManager, tag: String?) {
        listener = null
        super.show(manager, tag)
    }

    fun show(fm: FragmentManager, tag: String?, listener: (Int) -> Unit) {
        this.listener = listener
        super.show(fm, tag)
    }

    fun setRange(min: Int, max: Int, step: Int) {
        this.min = min
        this.max = max
        this.step = step
        processRange()
    }

    fun setValue(value: Int) {
        this.value = value
        binding?.numberPicker?.value = (value - min) / step
    }

    fun getValue(): Int {
        return list[binding!!.numberPicker.value].toInt()
    }


    private fun processRange() {
        binding?.numberPicker?.let {
            it.minValue = 0
            it.maxValue = (max - min) / step
            list = Array(it.maxValue + 1) { i ->
                (i * step + min).toString()
            }
            it.displayedValues = list
        }

        setValue(value)
    }

}