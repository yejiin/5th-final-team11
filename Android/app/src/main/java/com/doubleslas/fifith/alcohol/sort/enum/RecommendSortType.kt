package com.doubleslas.fifith.alcohol.sort.enum

import com.doubleslas.fifith.alcohol.App.Companion.getString
import com.doubleslas.fifith.alcohol.R


enum class RecommendSortType(
    override val text: String,
    override val sort: String,
    override val sortOption: String
) : SortType {
    Recommend(getString(R.string.sort_recommend), "recScore", "desc"),
    Review(getString(R.string.sort_review), "reviewCnt", "desc"),
    Star(getString(R.string.sort_star), "star", "desc")
}