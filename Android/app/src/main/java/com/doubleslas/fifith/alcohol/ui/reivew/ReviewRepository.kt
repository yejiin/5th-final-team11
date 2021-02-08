package com.doubleslas.fifith.alcohol.ui.reivew

import com.doubleslas.fifith.alcohol.model.base.ApiLiveData
import com.doubleslas.fifith.alcohol.model.base.RestClient
import com.doubleslas.fifith.alcohol.dto.ReviewList
import com.doubleslas.fifith.alcohol.dto.WriteReviewData

class ReviewRepository {
    private val service by lazy { RestClient.getReviewService() }
    fun writeReview(data: WriteReviewData, aid: Int): ApiLiveData<Any> {
        return service.writeReview(aid, data)
    }

    fun readReview(aid: Int, reviewPage: Int): ApiLiveData<ReviewList> {
        return service.readReview(aid, reviewPage)
    }

    fun writeComment(rid: Int, content: String): ApiLiveData<Any> {
        return service.writeComment(rid, content)
    }
}