package com.doubleslas.fifith.alcohol.viewmodel

import androidx.lifecycle.ViewModel
import com.doubleslas.fifith.alcohol.model.network.base.ApiLiveData
import com.doubleslas.fifith.alcohol.model.network.base.ApiStatus
import com.doubleslas.fifith.alcohol.model.network.base.MediatorApiLiveData
import com.doubleslas.fifith.alcohol.model.network.dto.ReviewDetailData
import com.doubleslas.fifith.alcohol.model.network.dto.WriteReviewData
import com.doubleslas.fifith.alcohol.model.repository.ReviewRepository

class ReviewViewModel(val reviewId: Int) : ViewModel() {
    private val repository by lazy { ReviewRepository() }

    fun sendReview(comment: String, star: Int, detail: ReviewDetailData? = null): ApiLiveData<Any> {
        val result = MediatorApiLiveData<Any>()

        val errorCode = checkData(comment, detail)
        if (errorCode != -1){
            result.value = ApiStatus.Error(errorCode, "")
            return result
        }

        val data = WriteReviewData(comment, star, detail)
        val liveData = repository.writeReview(data)
        result.addSource(liveData)

        return result
    }

    fun checkData(comment: String, detail: ReviewDetailData? = null): Int {
        if (comment.isEmpty()) {
            return ErrorCode.COMMENT.ordinal
        } else if (detail != null) {
            when {
                detail.date.isEmpty() -> {
                    return ErrorCode.DETAIL_DATE.ordinal
                }
                detail.place.isEmpty() -> {
                    return ErrorCode.DETAIL_PLACE.ordinal
                }
                detail.drink == 0 -> {
                    return ErrorCode.DETAIL_DRINK.ordinal
                }
                detail.price == 0 -> {
                    return ErrorCode.DETAIL_PRICE.ordinal
                }
            }
        }

        return -1
    }

    enum class ErrorCode {
        COMMENT,
        STAR,
        DETAIL_DATE,
        DETAIL_PLACE,
        DETAIL_DRINK,
        DETAIL_HANGOUT,
        DETAIL_PRICE
    }
}