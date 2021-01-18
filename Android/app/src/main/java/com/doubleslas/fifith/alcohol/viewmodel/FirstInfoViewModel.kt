package com.doubleslas.fifith.alcohol.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FirstInfoViewModel : ViewModel() {
    private val mCheckWine = MutableLiveData<Boolean>()
    var checkWine: LiveData<Boolean> = mCheckWine

    private val mCheckBeer = MutableLiveData<Boolean>()
    var checkBeer: LiveData<Boolean> = mCheckBeer

    private val mCheckLiquor = MutableLiveData<Boolean>()
    var checkLiquor: LiveData<Boolean> = mCheckLiquor

    init {
        mCheckBeer.value = false
        mCheckWine.value = false
        mCheckLiquor.value = false
    }

    fun setCheck() {

    }

    fun toggleCheckWine() {
        mCheckWine.value = !mCheckWine.value!!
    }

    fun toggleCheckBeer() {
        mCheckBeer.value = !mCheckBeer.value!!
    }

    fun toggleCheckLiquor() {
        mCheckLiquor.value = !mCheckLiquor.value!!
    }
}