package com.doubleslas.fifith.alcohol.utils

import com.doubleslas.fifith.alcohol.model.base.ApiLiveData

interface IPageLoaderViewModel {
    fun loadList()
    fun isFinishList(): Boolean
}