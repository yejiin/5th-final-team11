package com.doubleslas.fifith.alcohol.dto.interfaces

interface IPageLoadData<T>{
    fun getTotalCount(): Int
    fun getList(): List<T>
}