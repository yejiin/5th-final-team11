package com.doubleslas.fifith.alcohol.ui.detail

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class DetailReviewDecoration(private val divWidth: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.top = divWidth
    }

}