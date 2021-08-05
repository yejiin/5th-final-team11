package com.doubleslas.fifith.alcohol.dto

import com.google.gson.annotations.SerializedName

data class ReviewData(
    val rid: Int,
    val nickname: String,
    val content: String,
    val star: Float,
    val reviewDate: String,
    val detail: ReviewDetailData?,
    val comments: MutableList<ReviewCommentData>,
    @SerializedName("commentTotalCnt") var commentCount: Int,
    @SerializedName("love") var loveCount: Int,
    @SerializedName("loveClick") var isLove: Boolean?
) {

    // 클라 전용
    var visibleComment: Boolean = false
    var visibleCommentList: Boolean = false
    var cacheComment: String = ""
}