package com.doubleslas.fifith.alcohol.model.network.dto.interfaces

interface IPageLoadData<T>{
    fun getTotalCount(): Int
    fun getList(): List<T>
}