package com.doubleslas.fifith.alcohol.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.doubleslas.fifith.alcohol.model.base.ApiLiveData
import com.doubleslas.fifith.alcohol.dto.DetailList
import com.doubleslas.fifith.alcohol.ui.search.SearchListViewModel

class DetailViewModel(val id: Int) : ViewModel() {
    private val repository by lazy { DetailRepository() }

    fun getDetail(): ApiLiveData<DetailList> {
        return repository.getDetail(id)
    }



    class Factory(private val param: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
                DetailViewModel(param) as T
            } else {
                throw IllegalArgumentException()
            }
        }
    }
}