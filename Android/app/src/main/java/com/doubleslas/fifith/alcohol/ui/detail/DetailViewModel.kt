package com.doubleslas.fifith.alcohol.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.doubleslas.fifith.alcohol.dto.DetailData
import com.doubleslas.fifith.alcohol.dto.ReviewData
import com.doubleslas.fifith.alcohol.model.base.ApiLiveData
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

    fun isFinishReview(): Boolean {
        return reviewPageLoader.isFinish()
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