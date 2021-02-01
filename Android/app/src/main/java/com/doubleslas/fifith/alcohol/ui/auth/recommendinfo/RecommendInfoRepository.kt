package com.doubleslas.fifith.alcohol.ui.auth.recommendinfo

import com.doubleslas.fifith.alcohol.App
import com.doubleslas.fifith.alcohol.model.base.*
import com.doubleslas.fifith.alcohol.dto.recommend.RecommendInfoData

class RecommendInfoRepository {
    private val service by lazy { RestClient.getRecommendService() }


    fun submit(data: RecommendInfoData): ApiLiveData<Any> {
        val mediator = MediatorApiLiveData<Any>()
        val liveData = service.submitRecommendInfo(data)
        mediator.addSource(liveData, object : MediatorApiSuccessCallback<Any> {
            override fun onSuccess(code: Int, data: Any) {
                App.prefs.submitRecommendInfo = true
                mediator.value = ApiStatus.Success(code, data)
            }
        })
        return mediator
    }
}