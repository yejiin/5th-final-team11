package com.doubleslas.fifith.alcohol.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.doubleslas.fifith.alcohol.dto.DetailData
import com.doubleslas.fifith.alcohol.dto.LikeResponse
import com.doubleslas.fifith.alcohol.dto.ReviewCommentData
import com.doubleslas.fifith.alcohol.dto.ReviewData
import com.doubleslas.fifith.alcohol.model.base.ApiLiveData
import com.doubleslas.fifith.alcohol.model.base.ApiStatus
import com.doubleslas.fifith.alcohol.model.base.MediatorApiCallback
import com.doubleslas.fifith.alcohol.model.base.MediatorApiLiveData
import com.doubleslas.fifith.alcohol.ui.reivew.ReviewRepository
import com.doubleslas.fifith.alcohol.utils.PageLoader

class DetailViewModel(val aid: Int) : ViewModel() {
    private val detailRepository by lazy { DetailRepository() }
    private val reviewRepository by lazy { ReviewRepository() }
    private val reviewPageLoader by lazy { PageLoader<ReviewData>() }

    private val _infoLiveData = MediatorApiLiveData<DetailData>()
    val infoLiveData: ApiLiveData<DetailData> = _infoLiveData
    val reviewLiveData = reviewPageLoader.liveData
    private var isLoadingWriteComment = false


    fun getDetail() {
        val liveData = detailRepository.getDetail(aid)
        _infoLiveData.addSource(liveData)
    }

    fun resetReview() {
        reviewPageLoader.reset()
    }

    fun loadReview() {
        if (!reviewPageLoader.canLoadList()) return

        val liveData = reviewRepository.loadReviewList(aid, reviewPageLoader.page++)
        reviewPageLoader.addObserve(liveData)
    }

    fun setFavorite(value: Boolean) {
        detailRepository.setFavorite(aid, value)
    }

    fun isFinishReview(): Boolean {
        return reviewPageLoader.isFinish()
    }

    fun likeReview(item: ReviewData, value: Boolean): ApiLiveData<Any> {
        val mediator = MediatorApiLiveData<Any>()
        mediator.addSource(
            reviewRepository.likeReview(item.rid, value),
            object : MediatorApiCallback<LikeResponse> {
                override fun onLoading() {
                    mediator.value = ApiStatus.Loading
                }

                override fun onSuccess(code: Int, data: LikeResponse) {
                    item.loveCount = data.loveCount
                    item.isLove = data.love
                    mediator.value = ApiStatus.Success(code, data)
                }

                override fun onError(code: Int, msg: String) {
                    item.isLove = !value
                    mediator.value = ApiStatus.Error(code, msg)
                }
            }
        )

        return mediator
    }

    fun commentReview(item: ReviewData, content: String): ApiLiveData<Any>? {
        if (isLoadingWriteComment) return null

        val mediator = MediatorApiLiveData<Any>()

        mediator.addSource(
            reviewRepository.commentReview(item.rid, content),
            object : MediatorApiCallback<ReviewCommentData> {
                override fun onLoading() {
                    isLoadingWriteComment = true
                    mediator.value = ApiStatus.Loading
                }

                override fun onSuccess(code: Int, data: ReviewCommentData) {
                    isLoadingWriteComment = false
                    item.commentCount++
                    item.comments.add(0, data)
                    item.visibleCommentList = true
                    item.visibleComment = false
                    item.cacheComment = ""
                    mediator.value = ApiStatus.Success(code, data)
                }

                override fun onError(code: Int, msg: String) {
                    isLoadingWriteComment = false
                    mediator.value = ApiStatus.Error(code, msg)
                }
            }
        )

        return mediator
    }

    fun report(rid: Int, comment: String): ApiLiveData<Any> {
        return reviewRepository.reportReview(rid, comment)
    }

    fun reportComment(cid: Int, comment: String): ApiLiveData<Any> {
        return reviewRepository.reportComment(cid, comment)
    }

    class Factory(private val param: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
                DetailViewModel(param) as T
            } else {
                throw IllegalArgumentException()
            }
        }
    }
}