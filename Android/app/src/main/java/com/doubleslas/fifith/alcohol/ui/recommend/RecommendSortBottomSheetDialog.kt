package com.doubleslas.fifith.alcohol.ui.recommend

import android.os.Bundle
import android.view.View
import com.doubleslas.fifith.alcohol.enum.RecommendSortType
import com.doubleslas.fifith.alcohol.ui.common.BottomSheetMenu

class RecommendSortBottomSheetDialog : BottomSheetMenu() {
    private var onSortSelectListener: ((RecommendSortType) -> Unit)? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setList(MutableList(RecommendSortType.values().size) { i -> RecommendSortType.values()[i].text })

        setOnItemClickListener { it: String ->
            for (type in RecommendSortType.values()) {
                if (type.text == it) {
                    onSortSelectListener?.invoke(type)
                    return@setOnItemClickListener
                }
            }
        }
    }

    fun setOnSortSelectListener(listener: ((RecommendSortType) -> Unit)?) {
        onSortSelectListener = listener
    }

    fun setInitSort(sortType: RecommendSortType) {
        setSelectIndex(sortType.ordinal)
    }

}