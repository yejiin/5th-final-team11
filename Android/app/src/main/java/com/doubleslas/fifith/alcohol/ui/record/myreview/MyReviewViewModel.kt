package com.doubleslas.fifith.alcohol.ui.record.myreview

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.doubleslas.fifith.alcohol.dto.MyReviewData
import com.doubleslas.fifith.alcohol.model.base.ApiLiveData
import com.doubleslas.fifith.alcohol.model.base.ApiStatus
import com.doubleslas.fifith.alcohol.model.base.MediatorApiLiveData
import com.doubleslas.fifith.alcohol.sort.enum.MyReviewSortType
import com.doubleslas.fifith.alcohol.utils.IPageLoaderViewModel
import com.doubleslas.fifith.alcohol.utils.PageLoader

class MyReviewViewModel : ViewModel(), IPageLoaderViewModel {
    private val repository by lazy { MyReviewRepository() }
    private val pageLoader by lazy { PageLoader<MyReviewData>() }
    val listLiveData: ApiLiveData<List<MyReviewData>> = pageLoader.liveData

    private var sort = MyReviewSortType.Time
    var deleteMode = false

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

    fun deleteList(): ApiLiveData<Any> {
        val list = pageLoader.getList()
        val liveData = repository.deleteList(list.filter {
            return@filter it.isSelect
        })

        val mediator = MediatorApiLiveData<Any>()

        mediator.addSource(liveData, Observer {
            if (it is ApiStatus.Success) {
                pageLoader.reset()
                loadList()
            }
            if (it !is ApiStatus.Loading) mediator.removeSource(liveData)
        })

        return mediator
    }

    private fun initialize() {
        pageLoader.reset()
        loadList()
    }


}