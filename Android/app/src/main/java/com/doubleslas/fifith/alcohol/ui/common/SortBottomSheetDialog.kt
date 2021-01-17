package com.doubleslas.fifith.alcohol.ui.common

import android.os.Bundle
import android.view.View
import com.doubleslas.fifith.alcohol.enum.SortType

class SortBottomSheetDialog : BottomSheetMenu() {
    private var onSortSelectListener: ((SortType) -> Unit)? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setList(MutableList(SortType.values().size) { i -> SortType.values()[i].text })

        setOnItemClickListener { it: String ->
            for (type in SortType.values()) {
                if (type.text == it) {
                    onSortSelectListener?.invoke(type)
                    return@setOnItemClickListener
                }
            }
        }
    }

    fun setOnSortSelectListener(listener: ((SortType) -> Unit)?) {
        onSortSelectListener = listener
    }

    fun setInitSort(sortType: SortType){
        setSelectIndex(sortType.ordinal)
    }


}