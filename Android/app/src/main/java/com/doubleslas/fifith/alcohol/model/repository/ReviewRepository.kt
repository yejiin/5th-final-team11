package com.doubleslas.fifith.alcohol.model.repository

import com.doubleslas.fifith.alcohol.model.network.base.ApiLiveData
import com.doubleslas.fifith.alcohol.model.network.base.RestClient
import com.doubleslas.fifith.alcohol.model.network.dto.WriteReviewData

class ReviewRepository {
    private val service by lazy { RestClient.getReviewService() }
    fun writeReview(data: WriteReviewData, aid: Int): ApiLiveData<Any> {
        return service.writeReview(aid, data)
    }

    fun readReview(aid: Int, reviewPage: Int): ApiLiveData<Any> {
        return service.readReview(aid, reviewPage)
    }

    fun writeComment(rid: Int, content: String): ApiLiveData<Any> {
        return service.writeComment(rid, content)
    }
}