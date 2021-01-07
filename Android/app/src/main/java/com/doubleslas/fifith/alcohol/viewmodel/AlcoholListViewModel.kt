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

class AlcoholListViewModel(val category: String) : ViewModel() {
    private val repository by lazy { SearchRepository() }

    private val list = ArrayList<AlcoholSimpleData>()
    private val mediatorLiveData = MediatorApiLiveData<List<AlcoholSimpleData>>()
    val listLiveData: ApiLiveData<List<AlcoholSimpleData>> = mediatorLiveData

    private var sort = SortType.Popular

    private var page = 0
    private var totalCount: Int? = null

    fun loadList() {
        if (mediatorLiveData.value is ApiStatus.Loading) return
        if (totalCount != null && list.size >= totalCount!!) return

        mediatorLiveData.value = ApiStatus.Loading
        val liveData = repository.getList(category, page++, sort.sort, sort.sortOption)
        mediatorLiveData.addSource(liveData, object : MediatorApiSuccessCallback<SearchList> {
            override fun onSuccess(code: Int, data: SearchList) {
                totalCount = data.totalCount
                list.addAll(data.list)
                mediatorLiveData.value = ApiStatus.Success(code, list)
            }
        })
    }

    fun setSort(sortType: SortType) {
        sort = sortType
        page = 0
        totalCount = null
        list.clear()
        loadList()
    }


    class Factory(private val param: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(AlcoholListViewModel::class.java)) {
                AlcoholListViewModel(param) as T
            } else {
                throw IllegalArgumentException()
            }
        }

    }

}