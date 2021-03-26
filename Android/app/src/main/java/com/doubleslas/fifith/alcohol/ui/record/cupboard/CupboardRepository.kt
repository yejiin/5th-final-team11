package com.doubleslas.fifith.alcohol.ui.record.cupboard

import com.doubleslas.fifith.alcohol.dto.CupboardData
import com.doubleslas.fifith.alcohol.dto.CupboardList
import com.doubleslas.fifith.alcohol.model.base.ApiLiveData
import com.doubleslas.fifith.alcohol.model.base.RestClient
import com.doubleslas.fifith.alcohol.sort.enum.CupboardSortType

class CupboardRepository {
    private val service by lazy { RestClient.getCupboardService() }

    fun loadList(page: Int, sortType: CupboardSortType): ApiLiveData<CupboardList> {
        return service.getCupboardList(page, sortType.sort, sortType.sortOption)
    }


    fun loadLoveList(page: Int, sortType: CupboardSortType): ApiLiveData<CupboardList> {
        return service.getLoveList(page, sortType.sort, sortType.sortOption)
    }

    fun deleteLoveList(list: List<CupboardData>) : ApiLiveData<Any> {
        val builder = StringBuilder()
        for (item in list) {
            builder.append(item.aid)
            if (list[list.size - 1] != item) {
                builder.append(',')
            }
        }

        return service.deleteLoveList(builder.toString())
    }
}