package com.doubleslas.fifith.alcohol.viewmodel

import androidx.lifecycle.ViewModel
import com.doubleslas.fifith.alcohol.model.network.base.ApiLiveData
import com.doubleslas.fifith.alcohol.model.network.dto.DetailList
import com.doubleslas.fifith.alcohol.model.repository.DetailRepository

class DetailViewModel : ViewModel() {
    private val repository by lazy { DetailRepository() }

    fun getDetail(id: Int): ApiLiveData<DetailList> {
        return repository.getDetail(id)
    }
}