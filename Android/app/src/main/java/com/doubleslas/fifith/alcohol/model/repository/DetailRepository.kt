package com.doubleslas.fifith.alcohol.model.repository

import com.doubleslas.fifith.alcohol.model.network.base.ApiLiveData
import com.doubleslas.fifith.alcohol.model.network.base.RestClient

class DetailRepository {
    private val detailRetrofit by lazy { RestClient.getDetailService() }

    fun getDetail(id: Int): ApiLiveData<Any> {
        return detailRetrofit.getDetail(id)
    }
}