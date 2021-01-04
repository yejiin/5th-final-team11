package com.doubleslas.fifith.alcohol.enum

import com.doubleslas.fifith.alcohol.App.Companion.getString
import com.doubleslas.fifith.alcohol.R

enum class SortType(val text: String, val sort: String, val sortOption: String) {
    Popular(getString(R.string.sort_popular), "popularScore", "desc"),
    Review(getString(R.string.sort_review), "star", "desc"),
    Favorite(getString(R.string.sort_favorite), "loveCnt", "desc"),
    Abv(getString(R.string.sort_abv), "abv", "asc"),
    PriceAsc(getString(R.string.sort_price_asc), "price", "asc"),
    PriceDesc(getString(R.string.sort_price_desc), "price", "desc")
}