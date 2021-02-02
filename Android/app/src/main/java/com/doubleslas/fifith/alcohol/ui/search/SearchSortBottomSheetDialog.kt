package com.doubleslas.fifith.alcohol.ui.search

import android.os.Bundle
import android.view.View
import com.doubleslas.fifith.alcohol.enum.SearchSortType
import com.doubleslas.fifith.alcohol.ui.common.BottomSheetMenu

class SearchSortBottomSheetDialog : BottomSheetMenu() {
    private var onSortSelectListener: ((SearchSortType) -> Unit)? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setList(MutableList(SearchSortType.values().size) { i -> SearchSortType.values()[i].text })

        setOnItemClickListener { it: String ->
            for (type in SearchSortType.values()) {
                if (type.text == it) {
                    onSortSelectListener?.invoke(type)
                    return@setOnItemClickListener
                }
            }
        }
    }

    fun setOnSortSelectListener(listener: ((SearchSortType) -> Unit)?) {
        onSortSelectListener = listener
    }

    fun setInitSort(sortType: SearchSortType) {
        setSelectIndex(sortType.ordinal)
    }

}