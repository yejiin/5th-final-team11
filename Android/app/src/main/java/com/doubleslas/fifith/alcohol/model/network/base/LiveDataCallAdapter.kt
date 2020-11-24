package com.doubleslas.fifith.alcohol.model.network.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.doubleslas.fifith.alcohol.utils.LogUtil
import retrofit2.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


class LiveDataCallAdapter<R>(private val responseType: Type) : CallAdapter<R, LiveData<ApiStatus<R>>> {
    override fun responseType(): Type = responseType

    override fun adapt(call: Call<R>): LiveData<ApiStatus<R>> {
        val liveData = MutableLiveData<ApiStatus<R>>()
        liveData.value = ApiStatus.Loading

        call.enqueue(object : Callback<R> {
            override fun onFailure(call: Call<R>, t: Throwable) {
                LogUtil.e("HttpRetrofit", "onFailure", t)
            }

            override fun onResponse(call: Call<R>, response: Response<R>) {

                val code = response.code()
                if (response.isSuccessful) {
                    liveData.value = ApiStatus.Success(code, response.body()!!)
                    return
                }

                // Error 처리
                liveData.value = ApiStatus.Error(code, response.message())

                when (response.code()) {
                    401 -> {
                    }
                }
            }

        })

        return liveData
    }

    class Factory : CallAdapter.Factory() {
        override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
            if (getRawType(returnType) != LiveData::class.java) {
                return null
            }
            val observableType = getParameterUpperBound(0, returnType as ParameterizedType)
            val rawObservableType = getRawType(observableType)
            require(rawObservableType == ApiStatus::class.java) { "type must be a resource" }
            require(observableType is ParameterizedType) { "resource must be parameterized" }
            val bodyType = getParameterUpperBound(0, observableType)
            return LiveDataCallAdapter<Any?>(
                bodyType
            )
        }
    }
}