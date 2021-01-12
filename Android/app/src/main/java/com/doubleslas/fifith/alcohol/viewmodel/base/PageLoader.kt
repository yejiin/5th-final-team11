package com.doubleslas.fifith.alcohol.viewmodel.base

import com.doubleslas.fifith.alcohol.model.network.base.ApiLiveData
import com.doubleslas.fifith.alcohol.model.network.base.ApiStatus
import com.doubleslas.fifith.alcohol.model.network.base.MediatorApiCallback
import com.doubleslas.fifith.alcohol.model.network.base.MediatorApiLiveData
import com.doubleslas.fifith.alcohol.model.network.dto.interfaces.IPageLoadData

class PageLoader<T> {
    private val list = ArrayList<T>()
    private val mediatorLiveData = MediatorApiLiveData<List<T>>()
    val liveData: ApiLiveData<List<T>> = mediatorLiveData

    private var totalCount: Int? = null
    var page = 0

    fun isInit(): Boolean {
        return totalCount == null
    }

    fun canLoadList(): Boolean {
        if (mediatorLiveData.value is ApiStatus.Loading) return false
        if (totalCount != null && list.size >= totalCount!!) return false

        return true
    }

    fun <C : IPageLoadData<T>> addObserve(
        liveData: ApiLiveData<C>,
        callback: MediatorApiCallback<C>? = null
    ) {
        mediatorLiveData.value = ApiStatus.Loading
        mediatorLiveData.addSource(liveData, object : MediatorApiCallback<C> {
            override fun onLoading() {
                mediatorLiveData.value = ApiStatus.Loading
                callback?.onLoading()
            }

            override fun onSuccess(code: Int, data: C) {
                completeLoad(data.getTotalCount(), data.getList(), code)
                callback?.onSuccess(code, data)
            }

            override fun onError(code: Int, msg: String) {
                mediatorLiveData.value = ApiStatus.Error(code, msg)
                callback?.onError(code, msg)
            }
        })
    }

    fun completeLoad(totalCount: Int, l: List<T>, code: Int = 200) {
        this.totalCount = totalCount
        list.addAll(l)
        mediatorLiveData.value = ApiStatus.Success(code, list)
    }

    fun reset() {
        page = 0
        totalCount = null
        list.clear()
    }
}