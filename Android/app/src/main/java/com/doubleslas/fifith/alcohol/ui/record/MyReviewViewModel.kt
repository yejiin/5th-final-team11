package com.doubleslas.fifith.alcohol.ui.record

import androidx.lifecycle.ViewModel
import com.doubleslas.fifith.alcohol.dto.MyReviewData
import com.doubleslas.fifith.alcohol.enum.MyReviewSortType
import com.doubleslas.fifith.alcohol.model.base.ApiLiveData
import com.doubleslas.fifith.alcohol.utils.IPageLoaderViewModel
import com.doubleslas.fifith.alcohol.utils.PageLoader

class MyReviewViewModel : ViewModel(), IPageLoaderViewModel {
    private val repository by lazy { MyReviewRepository() }
    private val pageLoader by lazy { PageLoader<MyReviewData>() }
    val listLiveData: ApiLiveData<List<MyReviewData>> = pageLoader.liveData

    private var sort = MyReviewSortType.Time

    override fun loadList() {
        if (!pageLoader.canLoadList()) return

        val liveData = repository.getList(pageLoader.page++, sort)
        pageLoader.addObserve(liveData)
    }

    override fun isFinishList(): Boolean {
        return pageLoader.isFinish()
    }

    fun setSort(sortType: MyReviewSortType) {
        sort = sortType
        initialize()
    }

    private fun initialize() {
        pageLoader.reset()
        loadList()
    }

}