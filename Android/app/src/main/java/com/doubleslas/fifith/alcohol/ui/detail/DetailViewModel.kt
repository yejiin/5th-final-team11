package com.doubleslas.fifith.alcohol.ui.detail

import androidx.lifecycle.ViewModel
import com.doubleslas.fifith.alcohol.model.base.ApiLiveData
import com.doubleslas.fifith.alcohol.dto.DetailList

class DetailViewModel : ViewModel() {
    private val repository by lazy { DetailRepository() }

    fun getDetail(id: Int): ApiLiveData<DetailList> {
        return repository.getDetail(id)
    }
}