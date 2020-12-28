package com.doubleslas.fifith.alcohol.model.network.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer


typealias ApiLiveData<T> = LiveData<ApiStatus<T>>
typealias MutableApiLiveData<T> = MutableLiveData<ApiStatus<T>>
typealias MediatorApiLiveData<T> = MediatorImpl<ApiStatus<T>>

class MediatorImpl<T : ApiStatus<*>> : MediatorLiveData<T>() {
    fun <S : Any?> addSource(
        source: LiveData<ApiStatus<S>>,
        callback: MediatorApiCallback<in S>
    ) {
        addSource(source, Observer {
            if (it !is ApiStatus.Loading) removeSource(source)
            when (it) {
                is ApiStatus.Loading -> callback.onLoading()
                is ApiStatus.Success<S> -> {
                    removeSource(source)
                    callback.onSuccess(it.data)
                }
                is ApiStatus.Error -> {
                    removeSource(source)
                    callback.onError(it.code, it.message)
                }
            }
        })
    }

    fun addSource(source: LiveData<T>) {
        addSource(source, Observer {
            if (it !is ApiStatus.Loading) removeSource(source)
            value = it
        })
    }

}

interface MediatorApiCallback<T> {
    fun onLoading() {}
    fun onSuccess(data: T) {}
    fun onError(code: Int, msg: String) {}
}
