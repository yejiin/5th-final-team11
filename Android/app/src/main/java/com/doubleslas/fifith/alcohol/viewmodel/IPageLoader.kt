package com.doubleslas.fifith.alcohol.viewmodel

import android.os.Parcelable
import com.doubleslas.fifith.alcohol.model.network.base.ApiLiveData
import com.doubleslas.fifith.alcohol.model.network.dto.AlcoholSimpleData
import com.doubleslas.fifith.alcohol.model.network.dto.IPageList

interface ISortedPageLoader<T : IPageList<*>> : Parcelable {
    fun loadList(page: Int, sort: String, sortOption: String): ApiLiveData<T>
}