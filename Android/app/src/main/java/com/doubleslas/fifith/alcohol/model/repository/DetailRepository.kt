package com.doubleslas.fifith.alcohol.model.repository

import com.doubleslas.fifith.alcohol.model.network.base.ApiLiveData
import com.doubleslas.fifith.alcohol.model.network.base.RestClient
import com.doubleslas.fifith.alcohol.model.network.dto.DetailList

class DetailRepository {
    private val detailRetrofit by lazy { RestClient.getDetailService() }

    fun getDetail(id: Int): ApiLiveData<DetailList> {
        return detailRetrofit.getDetail(id)
    }
}