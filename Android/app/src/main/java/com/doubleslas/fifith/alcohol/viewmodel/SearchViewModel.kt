package com.doubleslas.fifith.alcohol.viewmodel

import androidx.lifecycle.ViewModel
import com.doubleslas.fifith.alcohol.model.network.dto.SearchHistoryData
import com.doubleslas.fifith.alcohol.model.repository.SearchRepository

class SearchViewModel : ViewModel() {
    private val repository by lazy { SearchRepository() }
    private val historyData: MutableList<SearchHistoryData> by lazy { repository.getSearchHistory() }

    fun getHistoryList(): List<SearchHistoryData> = historyData
}