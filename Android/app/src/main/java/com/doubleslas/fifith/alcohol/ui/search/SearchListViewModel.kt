package com.doubleslas.fifith.alcohol.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.doubleslas.fifith.alcohol.dto.AlcoholSimpleData
import com.doubleslas.fifith.alcohol.sort.enum.SearchSortType
import com.doubleslas.fifith.alcohol.model.base.ApiLiveData
import com.doubleslas.fifith.alcohol.utils.IPageLoaderViewModel
import com.doubleslas.fifith.alcohol.utils.PageLoader

class SearchListViewModel(private val category: String) : ViewModel(), IPageLoaderViewModel {
    private val repository = SearchRepository()
    val pageLoader = PageLoader<AlcoholSimpleData>()
    val listLiveData: ApiLiveData<List<AlcoholSimpleData>> = pageLoader.liveData

    var sort =
        globalSortType
        private set

    fun initList(): Boolean {
        if (pageLoader.isInit() || sort != globalSortType) {
            loadList()
            return true
        }
        return false
    }

    override fun loadList() {
        if (sort != globalSortType) {
            setSort(globalSortType)
            return
        }
        if (!pageLoader.canLoadList()) return

        val liveData = repository.getList(category, pageLoader.page++, sort)
        pageLoader.addObserve(liveData)
    }

    fun setSort(sortType: SearchSortType) {
        if (sort == sortType) return
        sort = sortType
        globalSortType = sortType
        pageLoader.reset()
        loadList()
    }

    override fun isFinishList(): Boolean {
        return pageLoader.isFinish()
    }

    class Factory(private val param: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(SearchListViewModel::class.java)) {
                SearchListViewModel(param) as T
            } else {
                throw IllegalArgumentException()
            }
        }
    }

    companion object {
        private var globalSortType: SearchSortType = SearchSortType.Popular
    }
}