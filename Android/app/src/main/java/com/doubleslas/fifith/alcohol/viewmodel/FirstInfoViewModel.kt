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

    fun getTextLiquorType(): List<String> {
        return listOf(
            "진",
            "럼",
            "위스키",
            "보드카",
            "데낄라",
            "리큐르"
        )
    }

    fun getTextLiquorTaste(): List<String> {
        return listOf(
            "상큼한",
            "풀 향의",
            "쓴",
            "독한",
            "톡 쏘는",
            "이국적인",
            "깔끔한",
            "오크향의",
            "스모키한",
            "달달한",
            "과일맛의",
            "쌉싸름한",
            "밀키한",
            "신맛의"
        )
    }

    fun getTextWineType(): List<String> {
        return listOf(
            "레드 와인",
            "화이트 와인",
            "로제 와인",
            "스파클링 와인",
            "디저트 와인",
            "포티파이드 와인"
        )
    }

    fun getTextBeerType(): List<Pair<String, List<String>>> {
        return listOf(
            Pair(
                "에일",
                listOf(
                    "페일에일",
                    "IPA",
                    "바이젠",
                    "포터",
                    "스타우트"
                )
            ),
            Pair(
                "라거",
                listOf(
                    "필스너",
                    "둔켈",
                    "페일라거"
                )
            ),
            Pair("람빅", listOf())
        )
    }

    fun getTextBeerPlace(): List<String> {
        return listOf(
            "한국",
            "미국",
            "유럽",
            "일본",
            "그 외"
        )
    }
}