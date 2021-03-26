package com.doubleslas.fifith.alcohol.ui.record.cupboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.doubleslas.fifith.alcohol.dto.CupboardData
import com.doubleslas.fifith.alcohol.model.base.ApiStatus
import com.doubleslas.fifith.alcohol.model.base.MediatorApiLiveData
import com.doubleslas.fifith.alcohol.sort.enum.CupboardSortType
import com.doubleslas.fifith.alcohol.utils.PageLoader

class CupboardViewModel : ViewModel() {
    private val repository by lazy { CupboardRepository() }
    private val pageLoader by lazy { PageLoader<CupboardData>() }
    private val lovePageLoader by lazy { PageLoader<CupboardData>() }

    var isLoveMode = false
        private set

    var deleteMode = false

    private val _cupboardSort = MutableLiveData<CupboardSortType>()
    val cupboardSort: LiveData<CupboardSortType> = _cupboardSort

    private val _listLiveData = MediatorApiLiveData<List<CupboardData>>()
    val listLiveData = _listLiveData

    init {
        setSort(CupboardSortType.Time)
        _listLiveData.addSource(pageLoader.liveData, Observer {
            if (isLoveMode) return@Observer
            _listLiveData.value = it
        })

        _listLiveData.addSource(lovePageLoader.liveData, Observer {
            if (!isLoveMode) return@Observer
            _listLiveData.value = it
        })

    }


    fun setSort(type: CupboardSortType) {
        _cupboardSort.value = type
        pageLoader.reset()
        lovePageLoader.reset()
        loadList()
    }

    fun toggleMode() {
        isLoveMode = !isLoveMode
        _listLiveData.value =
            if (isLoveMode) lovePageLoader.liveData.value else pageLoader.liveData.value
    }

    fun loadList() {
        if (isLoveMode) loadLoveList()
        else loadCupboardList()
    }

    fun deleteList() {
        val pageLoader = if (isLoveMode) lovePageLoader else pageLoader
        val list = pageLoader.getList()
        val liveData = repository.deleteLoveList(list)

        _listLiveData.addSource(liveData, Observer {
            if (it is ApiStatus.Success) {
                pageLoader.reset()
                loadList()
            }
            if (it !is ApiStatus.Loading) _listLiveData.removeSource(liveData)
        })
    }

    private fun loadCupboardList() {
        if (!pageLoader.canLoadList()) return

        val liveData = repository.loadList(pageLoader.page++, cupboardSort.value!!)
        pageLoader.addObserve(liveData)
    }

    private fun loadLoveList() {
        if (!lovePageLoader.canLoadList()) return

        val liveData = repository.loadLoveList(lovePageLoader.page++, cupboardSort.value!!)
        lovePageLoader.addObserve(liveData)
    }

    fun isFinish(): Boolean {
        return if (isLoveMode) lovePageLoader.isFinish()
        else pageLoader.isFinish()
    }

    fun allReset(){
        pageLoader.reset()
        lovePageLoader.reset()
    }

}