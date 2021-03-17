package com.doubleslas.fifith.alcohol.ui.detail

import com.doubleslas.fifith.alcohol.dto.DetailData
import com.doubleslas.fifith.alcohol.dto.FavoriteBody
import com.doubleslas.fifith.alcohol.model.base.ApiLiveData
import com.doubleslas.fifith.alcohol.model.base.RestClient

class DetailRepository {
    private val detailRetrofit by lazy { RestClient.getDetailService() }

    fun getDetail(id: Int): ApiLiveData<DetailData> {
        return detailRetrofit.getDetail(id)
    }

    fun setFavorite(id: Int, value: Boolean): ApiLiveData<Any> {
        return detailRetrofit.setFavorite(id, FavoriteBody(value))
    }
}