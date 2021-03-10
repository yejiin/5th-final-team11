package com.doubleslas.fifith.alcohol.ui.record.cupboard

import android.os.Bundle
import android.view.View
import com.doubleslas.fifith.alcohol.enum.CupboardSortType
import com.doubleslas.fifith.alcohol.ui.common.BottomSheetMenu

class CupboardSortBottomSheetDialog : BottomSheetMenu() {
    private var onSortSelectListener: ((CupboardSortType) -> Unit)? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setList(MutableList(CupboardSortType.values().size) { i -> CupboardSortType.values()[i].text })

        setOnItemClickListener { it: String ->
            for (type in CupboardSortType.values()) {
                if (type.text == it) {
                    onSortSelectListener?.invoke(type)
                    return@setOnItemClickListener
                }
            }
        }
    }

    fun setOnSortSelectListener(listener: ((CupboardSortType) -> Unit)?) {
        onSortSelectListener = listener
    }

    fun setInitSort(sortType: CupboardSortType) {
        setSelectIndex(sortType.ordinal)
    }

}