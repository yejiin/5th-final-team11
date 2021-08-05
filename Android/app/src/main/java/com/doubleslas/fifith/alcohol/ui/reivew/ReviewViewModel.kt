package com.doubleslas.fifith.alcohol.ui.reivew

import androidx.lifecycle.ViewModel
import com.doubleslas.fifith.alcohol.dto.ReviewDetailData
import com.doubleslas.fifith.alcohol.dto.WriteReviewData
import com.doubleslas.fifith.alcohol.model.base.ApiLiveData
import com.doubleslas.fifith.alcohol.model.base.ApiStatus
import com.doubleslas.fifith.alcohol.model.base.MediatorApiLiveData

class ReviewViewModel : ViewModel() {
    private val repository by lazy { ReviewRepository() }

    fun sendReview(
        comment: String,
        star: Int,
        detail: ReviewDetailData?,
        aid: Int
    ): ApiLiveData<Any> {
        val result = MediatorApiLiveData<Any>()

        val errorCode = checkValidate(comment, detail)
        if (errorCode != null) {
            result.value = errorCode
            return result
        }

        val data = WriteReviewData(comment, star, detail)
        val liveData = repository.writeReview(data, aid)
        result.addSource(liveData)

        return result
    }


    fun checkValidate(comment: String, detail: ReviewDetailData? = null): ReviewValidateFail? {
        if (comment.length < 20) {
            return ReviewValidateFail.CommentTooShort
        }
        return null
    }


    sealed class ReviewValidateFail : ApiStatus.ValidateFail() {
        object CommentTooShort : ReviewValidateFail()
    }
}