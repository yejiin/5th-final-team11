package com.doubleslas.fifith.alcohol.ui.record.cupboard

import androidx.lifecycle.ViewModel
import com.doubleslas.fifith.alcohol.dto.CupboardData
import com.doubleslas.fifith.alcohol.enum.CupboardSortType
import com.doubleslas.fifith.alcohol.utils.PageLoader

class CupboardViewModel : ViewModel() {
    private val repository by lazy { CupboardRepository() }
    private val pageLoader by lazy { PageLoader<CupboardData>() }

    val listLiveData = pageLoader.liveData

    fun loadCupboardList() {
        if (!pageLoader.canLoadList()) return

        val liveData = repository.loadList(pageLoader.page++, CupboardSortType.Abv)
        pageLoader.addObserve(liveData)
    }

    fun isFinish(): Boolean {
        return pageLoader.isFinish()
    }
}