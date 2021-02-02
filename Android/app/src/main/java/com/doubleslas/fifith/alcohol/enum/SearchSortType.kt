package com.doubleslas.fifith.alcohol.enum

import com.doubleslas.fifith.alcohol.App
import com.doubleslas.fifith.alcohol.R


enum class SearchSortType(val text: String, val sort: String, val sortOption: String) {
    Popular(App.getString(R.string.sort_popular), "popularScore", "desc"),
    Review(App.getString(R.string.sort_review), "star", "desc"),
    Favorite(App.getString(R.string.sort_favorite), "loveCnt", "desc"),
    Abv(App.getString(R.string.sort_abv), "abv", "asc"),
    PriceAsc(App.getString(R.string.sort_price_asc), "price", "asc"),
    PriceDesc(App.getString(R.string.sort_price_desc), "price", "desc")
}