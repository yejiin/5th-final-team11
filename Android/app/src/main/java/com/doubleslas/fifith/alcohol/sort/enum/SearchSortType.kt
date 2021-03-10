package com.doubleslas.fifith.alcohol.sort.enum

import com.doubleslas.fifith.alcohol.App
import com.doubleslas.fifith.alcohol.R


enum class SearchSortType(
    override val text: String,
    override val sort: String,
    override val sortOption: String
) : SortType {
    Popular(App.getString(R.string.sort_popular), "popularScore", "desc"),
    Review(App.getString(R.string.sort_review), "star", "desc"),
    Favorite(App.getString(R.string.sort_favorite), "loveCnt", "desc"),
    Abv(App.getString(R.string.sort_abv), "abv", "asc"),
    PriceAsc(App.getString(R.string.sort_price_asc), "price", "asc"),
    PriceDesc(App.getString(R.string.sort_price_desc), "price", "desc")
}