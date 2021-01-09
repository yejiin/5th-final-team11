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
        mHistoryList.add(keyword)

        repository.saveSearchHistory(mHistoryList)
    }

    private fun deleteHistory(keyword: String){
        mHistoryList.remove(keyword)

        repository.saveSearchHistory(mHistoryList)
    }
}