package com.doubleslas.fifith.alcohol.ui.record.cupboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.doubleslas.fifith.alcohol.dto.CupboardData
import com.doubleslas.fifith.alcohol.enum.CupboardSortType
import com.doubleslas.fifith.alcohol.utils.PageLoader

class CupboardViewModel : ViewModel() {
    private val repository by lazy { CupboardRepository() }
    private val pageLoader by lazy { PageLoader<CupboardData>() }

    private val _cupboardSort = MutableLiveData<CupboardSortType>()
    val cupboardSort: LiveData<CupboardSortType> = _cupboardSort

    init {
        setSort(CupboardSortType.Time)
    }

    val listLiveData = pageLoader.liveData

    fun setSort(type: CupboardSortType) {
        _cupboardSort.value = type
        pageLoader.reset()
    }

    fun loadCupboardList() {
        if (!pageLoader.canLoadList()) return

        val liveData = repository.loadList(pageLoader.page++, cupboardSort.value!!)
        pageLoader.addObserve(liveData)
    }

    fun isFinish(): Boolean {
        return pageLoader.isFinish()
    }
}