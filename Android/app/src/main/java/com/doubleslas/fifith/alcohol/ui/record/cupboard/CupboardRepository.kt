package com.doubleslas.fifith.alcohol.ui.record.cupboard

import com.doubleslas.fifith.alcohol.dto.CupboardList
import com.doubleslas.fifith.alcohol.enum.CupboardSortType
import com.doubleslas.fifith.alcohol.enum.MyReviewSortType
import com.doubleslas.fifith.alcohol.model.base.ApiLiveData
import com.doubleslas.fifith.alcohol.model.base.RestClient

class CupboardRepository {
    private val service by lazy { RestClient.getCupboardService() }

    fun loadList(page: Int, sortType: CupboardSortType): ApiLiveData<CupboardList> {
        return service.getCupboardList(page, sortType.sort, sortType.sortOption)
    }
}