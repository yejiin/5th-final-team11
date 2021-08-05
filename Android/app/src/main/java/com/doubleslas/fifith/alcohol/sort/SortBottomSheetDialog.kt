package com.doubleslas.fifith.alcohol.sort

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import com.doubleslas.fifith.alcohol.sort.enum.CupboardSortType
import com.doubleslas.fifith.alcohol.sort.enum.SortType
import com.doubleslas.fifith.alcohol.ui.common.BottomSheetMenu

class SortBottomSheetDialog<T : SortType>
    (val list: Array<T>) : BottomSheetMenu() {

    private var onSortSelectListener: ((T) -> Unit)? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setList(MutableList(list.size) { i -> list[i].text })

        setOnItemClickListener { position, _ ->
            for (type in CupboardSortType.values()) {
                onSortSelectListener?.invoke(list[position])
            }
        }

    }

    fun show(fm: FragmentManager, sortType: SortType){
        setSelectIndex(list.indexOf(sortType))
        show(fm, null)
    }

    fun setOnSortSelectListener(listener: ((T) -> Unit)?) {
        onSortSelectListener = listener
    }
}