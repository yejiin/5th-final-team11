package com.doubleslas.fifith.alcohol.enum

import com.doubleslas.fifith.alcohol.App
import com.doubleslas.fifith.alcohol.App.Companion.getString
import com.doubleslas.fifith.alcohol.R


enum class RecommendSortType(val text: String, val sort: String, val sortOption: String) {
    Recommend(getString(R.string.sort_recommend), "recScore", "desc"),
    Review(getString(R.string.sort_review), "reviewCnt", "desc"),
    Star(getString(R.string.sort_star), "star", "desc")
}