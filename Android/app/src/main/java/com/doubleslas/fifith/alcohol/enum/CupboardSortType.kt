package com.doubleslas.fifith.alcohol.enum

import com.doubleslas.fifith.alcohol.App
import com.doubleslas.fifith.alcohol.R


enum class CupboardSortType(val text: String, val sort: String, val sortOption: String) {
    Time(App.getString(R.string.sort_time), "latest", "desc"),
    Abv(App.getString(R.string.sort_abv), "abv", "desc")
}