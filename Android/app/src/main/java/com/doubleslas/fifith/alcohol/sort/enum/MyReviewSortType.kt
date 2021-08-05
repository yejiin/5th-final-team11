package com.doubleslas.fifith.alcohol.sort.enum

import com.doubleslas.fifith.alcohol.App
import com.doubleslas.fifith.alcohol.R


enum class MyReviewSortType(
    override val text: String,
    override val sort: String,
    override val sortOption: String
) : SortType {
    Time(App.getString(R.string.sort_time), "latest", "desc"),
    Abv(App.getString(R.string.sort_abv), "abv", "desc")
}