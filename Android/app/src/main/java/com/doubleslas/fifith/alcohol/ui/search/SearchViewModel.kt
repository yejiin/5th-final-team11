package com.doubleslas.fifith.alcohol.ui.search

import androidx.lifecycle.ViewModel
import com.doubleslas.fifith.alcohol.sort.enum.SearchSortType
import com.doubleslas.fifith.alcohol.model.base.ApiLiveData
import com.doubleslas.fifith.alcohol.dto.AlcoholSimpleData
import com.doubleslas.fifith.alcohol.utils.PageLoader

class SearchViewModel : ViewModel() {
    private val repository by lazy { SearchRepository() }
    private val mHistoryList by lazy { repository.getSearchHistory() }
    private val pageLoader =
        PageLoader<AlcoholSimpleData>()
    val listLiveData: ApiLiveData<List<AlcoholSimpleData>> = pageLoader.liveData

    private var sort = SearchSortType.Popular

    fun getHistoryList(): List<String> = mHistoryList

    private fun addHistory(keyword: String) {
        if (mHistoryList.contains(keyword)) return
        mHistoryList.add(keyword)

        if (mHistoryList.size > 10) mHistoryList.removeAt(mHistoryList.size - 1)

        repository.saveSearchHistory(mHistoryList)
    }

    fun deleteHistory(index: Int) {
        mHistoryList.removeAt(index)
        repository.saveSearchHistory(mHistoryList)
    }

    fun search(keyword: String) {
        if (!pageLoader.canLoadList()) return

        val liveData = repository.search(keyword, pageLoader.page++, sort)

        pageLoader.addObserve(liveData)
        addHistory(keyword)
    }
}