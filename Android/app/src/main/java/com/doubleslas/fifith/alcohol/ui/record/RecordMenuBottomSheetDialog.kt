package com.doubleslas.fifith.alcohol.ui.record

import com.doubleslas.fifith.alcohol.R
import com.doubleslas.fifith.alcohol.ui.common.BottomSheetMenu

class RecordMenuBottomSheetDialog : BottomSheetMenu() {
    init {
        setList(listOf(getString(R.string.record_delete)))
    }
}