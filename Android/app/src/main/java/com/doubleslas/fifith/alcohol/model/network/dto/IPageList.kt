package com.doubleslas.fifith.alcohol.model.network.dto

import com.doubleslas.fifith.alcohol.viewmodel.ISortedPageLoader
import com.google.gson.annotations.SerializedName

interface IPageList<T> {
    fun getList(): List<T>
    fun getTotalCount(): Int
}
