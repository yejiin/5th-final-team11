package com.doubleslas.fifith.alcohol.ui.reivew

import com.doubleslas.fifith.alcohol.dto.*
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

    fun commentReview(rid: Int, content: String): ApiLiveData<ReviewCommentData> {
        return service.writeComment(rid, ReviewCommentBody(content))
    }

    fun likeReview(rid: Int, value: Boolean): ApiLiveData<LikeResponse> {
        return service.likeReview(rid, ReviewLikeBody(value))
    }

    fun reportReview(rid: Int, comment: String): ApiLiveData<Any> {
        return service.reportReview(rid, ReportReviewBody(comment))
    }


    fun reportComment(cid: Int, comment: String): ApiLiveData<Any> {
        return service.reportComment(cid, ReportReviewBody(comment))
    }
}