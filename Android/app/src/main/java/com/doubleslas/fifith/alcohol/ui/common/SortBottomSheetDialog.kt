package com.doubleslas.fifith.alcohol.ui.common

import android.os.Bundle
import android.view.View
import com.doubleslas.fifith.alcohol.App.Companion.getString
import com.doubleslas.fifith.alcohol.R

class SortBottomSheetDialog : BottomSheetMenu() {
    private var onItemClickListener: ((String, String) -> Unit)? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setList(MutableList(SortType.values().size) { i -> SortType.values()[i].text })

        super.setOnItemClickListener {
            for (type in SortType.values()) {
                if (type.text == it) {
                    onItemClickListener?.invoke(type.sort, type.sortOption)
                    return@setOnItemClickListener
                }
            }
        }
    }

    fun setOnItemClickListener(listener: ((String, String) -> Unit)?) {
        onItemClickListener = listener
    }


    enum class SortType(val text: String, val sort: String, val sortOption: String) {
        Popular(getString(R.string.sort_popular), "popularScore", "desc"),
        Review(getString(R.string.sort_review), "star", "desc"),
        Favorite(getString(R.string.sort_favorite), "loveCnt", "desc"),
        Abv(getString(R.string.sort_abv), "abv", "asc"),
        PriceAsc(getString(R.string.sort_price_asc), "price", "asc"),
        PriceDesc(getString(R.string.sort_price_desc), "price", "desc")
    }

}