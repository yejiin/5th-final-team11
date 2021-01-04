package com.doubleslas.fifith.alcohol.ui.common

import android.os.Bundle
import android.view.View
import com.doubleslas.fifith.alcohol.App.Companion.getString
import com.doubleslas.fifith.alcohol.R
import com.doubleslas.fifith.alcohol.enum.SortType

class SortBottomSheetDialog : BottomSheetMenu() {
    private var onItemClickListener: ((SortType) -> Unit)? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setList(MutableList(SortType.values().size) { i -> SortType.values()[i].text })

        super.setOnItemClickListener {
            for (type in SortType.values()) {
                if (type.text == it) {
                    onItemClickListener?.invoke(type)
                    return@setOnItemClickListener
                }
            }
        }
    }

    fun setOnItemClickListener(listener: ((SortType) -> Unit)?) {
        onItemClickListener = listener
    }



}