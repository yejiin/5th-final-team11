package com.doubleslas.fifith.alcohol.ui.reivew

import com.doubleslas.fifith.alcohol.dto.ReviewCommentBody
import com.doubleslas.fifith.alcohol.dto.ReviewLikeBody
import com.doubleslas.fifith.alcohol.dto.ReviewList
import com.doubleslas.fifith.alcohol.dto.WriteReviewData
import com.doubleslas.fifith.alcohol.model.base.ApiLiveData
import com.doubleslas.fifith.alcohol.model.base.RestClient

class ReviewRepository {
    private val service by lazy { RestClient.getReviewService() }
    fun writeReview(data: WriteReviewData, aid: Int): ApiLiveData<Any> {
        return service.writeReview(aid, data)
    }

    fun loadReviewList(aid: Int, reviewPage: Int): ApiLiveData<ReviewList> {
        return service.readReview(aid, reviewPage)
    }

    fun commentReview(rid: Int, content: String): ApiLiveData<Any> {
        return service.writeComment(rid, ReviewCommentBody(content))
    }

    fun likeReview(rid: Int, value: Boolean): ApiLiveData<Any> {
        return service.likeReview(rid, ReviewLikeBody(value))
    }
}