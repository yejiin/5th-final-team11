package com.doubleslas.fifith.alcohol.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.doubleslas.fifith.alcohol.enum.SortType
import com.doubleslas.fifith.alcohol.model.network.base.ApiLiveData
import com.doubleslas.fifith.alcohol.model.network.base.ApiStatus
import com.doubleslas.fifith.alcohol.model.network.base.MediatorApiLiveData
import com.doubleslas.fifith.alcohol.model.network.base.MediatorApiSuccessCallback
import com.doubleslas.fifith.alcohol.model.network.dto.AlcoholSimpleData
import com.doubleslas.fifith.alcohol.model.network.dto.IPageList
import com.doubleslas.fifith.alcohol.model.repository.SearchRepository

class AlcoholListViewModel<T : IPageList<AlcoholSimpleData>>(private val loader: ISortedPageLoader<T>) :
    ViewModel() {

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
        val liveData = loader.loadList(page++, sort.sort, sort.sortOption)
//        val liveData = repository.getList(category, page++, sort.sort, sort.sortOption)
        mediatorLiveData.addSource(
            liveData,
            object : MediatorApiSuccessCallback<IPageList<AlcoholSimpleData>> {
                override fun onSuccess(code: Int, data: IPageList<AlcoholSimpleData>) {
                    totalCount = data.getTotalCount()
                    list.addAll(data.getList())
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


    class Factory<T : IPageList<AlcoholSimpleData>>(private val param: ISortedPageLoader<T>) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(AlcoholListViewModel::class.java)) {
                AlcoholListViewModel(param) as T
            } else {
                throw IllegalArgumentException()
            }
        }
    }

}