package com.doubleslas.fifith.alcohol.ui.record

import android.os.Bundle
import android.view.View
import com.doubleslas.fifith.alcohol.R
import com.doubleslas.fifith.alcohol.ui.common.BottomSheetMenu

class RecordMenuBottomSheetDialog : BottomSheetMenu() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setList(listOf(getString(R.string.record_delete)))
    }
}