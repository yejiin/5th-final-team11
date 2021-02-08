package com.doubleslas.fifith.alcohol.ui.detail

import com.doubleslas.fifith.alcohol.model.base.ApiLiveData
import com.doubleslas.fifith.alcohol.model.base.RestClient
import com.doubleslas.fifith.alcohol.dto.DetailList

class DetailRepository {
    private val detailRetrofit by lazy { RestClient.getDetailService() }

    fun getDetail(id: Int): ApiLiveData<DetailList> {
        return detailRetrofit.getDetail(id)
    }
}