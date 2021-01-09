package com.doubleslas.fifith.alcohol.viewmodel

import androidx.lifecycle.ViewModel
import com.doubleslas.fifith.alcohol.model.repository.SearchRepository

class SearchViewModel : ViewModel() {
    private val repository by lazy { SearchRepository() }
    private val mHistoryList by lazy { repository.getSearchHistory() }

    fun getHistoryList(): List<String> = mHistoryList

    fun search(keyword: String) {
        addHistory(keyword)
    }

    private fun addHistory(keyword: String) {
        if (mHistoryList.contains(keyword)) return
        mHistoryList.add(keyword)

        if (mHistoryList.size > 10) mHistoryList.removeAt(mHistoryList.size - 1)

        repository.saveSearchHistory(mHistoryList)
    }

    fun deleteHistory(index: Int){
        mHistoryList.removeAt(index)

        repository.saveSearchHistory(mHistoryList)
    }
}