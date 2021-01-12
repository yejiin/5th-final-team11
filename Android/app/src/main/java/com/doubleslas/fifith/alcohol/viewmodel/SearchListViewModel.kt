package com.doubleslas.fifith.alcohol.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.doubleslas.fifith.alcohol.enum.SortType
import com.doubleslas.fifith.alcohol.model.network.base.ApiLiveData
import com.doubleslas.fifith.alcohol.model.network.base.ApiStatus
import com.doubleslas.fifith.alcohol.model.network.base.MediatorApiLiveData
import com.doubleslas.fifith.alcohol.model.network.base.MediatorApiSuccessCallback
import com.doubleslas.fifith.alcohol.model.network.dto.AlcoholSimpleData
import com.doubleslas.fifith.alcohol.model.network.dto.SearchList
import com.doubleslas.fifith.alcohol.model.repository.SearchRepository

class SearchListViewModel(private val category: String) : ViewModel() {
    private val repository = SearchRepository()
    private val list = ArrayList<AlcoholSimpleData>()
    private val listMediatorLiveData = MediatorApiLiveData<List<AlcoholSimpleData>>()
    val listLiveData: ApiLiveData<List<AlcoholSimpleData>> = listMediatorLiveData

    var sort = globalSortType
        private set


    private var page = 0
    private var totalCount: Int? = null

    fun initList(): Boolean {
        if (totalCount == null || sort != globalSortType){
            loadList()
            return true
        }
        return false
    }

    fun loadList() {
        if (sort != globalSortType) {
            setSort(globalSortType)
            return
        }
        if (listMediatorLiveData.value is ApiStatus.Loading) return
        if (totalCount != null && list.size >= totalCount!!) return


        listMediatorLiveData.value = ApiStatus.Loading
        val liveData = repository.getList(category, page++, sort.sort, sort.sortOption)
        listMediatorLiveData.addSource(
            liveData,
            object : MediatorApiSuccessCallback<SearchList> {
                override fun onSuccess(code: Int, data: SearchList) {
                    totalCount = data.totalCount
                    list.addAll(data.list)
                    listMediatorLiveData.value = ApiStatus.Success(code, list)
                }
            })
    }

    fun setSort(sortType: SortType) {
        if (sort == sortType) return
        sort = sortType
        globalSortType = sortType
        page = 0
        totalCount = null
        list.clear()
        loadList()
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
        private var globalSortType: SortType = SortType.Popular
    }
}