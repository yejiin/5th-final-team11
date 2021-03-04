package com.doubleslas.fifith.alcohol.ui.reivew

import androidx.lifecycle.ViewModel
import com.doubleslas.fifith.alcohol.dto.ReviewDetailData
import com.doubleslas.fifith.alcohol.dto.ReviewList
import com.doubleslas.fifith.alcohol.dto.WriteReviewData
import com.doubleslas.fifith.alcohol.model.base.ApiLiveData
import com.doubleslas.fifith.alcohol.model.base.ApiStatus
import com.doubleslas.fifith.alcohol.model.base.MediatorApiLiveData

class ReviewViewModel() : ViewModel() {
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
        if (comment.isEmpty()) {
            return ReviewValidateFail.CommentEmpty
        } else if (detail != null) {
            when {
                detail.date!!.isEmpty() -> {
                    return ReviewValidateFail.DetailDateEmpty
                }
                detail.place!!.isEmpty() -> {
                    return ReviewValidateFail.DetailPlaceEmpty
                }
                detail.drink == 0 -> {
                    return ReviewValidateFail.DetailDrinkEmpty
                }
                detail.price == 0 -> {
                    return ReviewValidateFail.DetailPriceEmpty
                }
            }
        }

        return null
    }


    sealed class ReviewValidateFail : ApiStatus.ValidateFail() {
        object CommentEmpty : ReviewValidateFail()
        object DetailDateEmpty : ReviewValidateFail()
        object DetailPlaceEmpty : ReviewValidateFail()
        object DetailDrinkEmpty : ReviewValidateFail()
        object DetailHangoutEmpty : ReviewValidateFail()
        object DetailPriceEmpty : ReviewValidateFail()
    }
}