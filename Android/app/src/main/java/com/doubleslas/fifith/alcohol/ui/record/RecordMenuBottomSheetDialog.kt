package com.doubleslas.fifith.alcohol.ui.record

import android.os.Bundle
import com.doubleslas.fifith.alcohol.R
import com.doubleslas.fifith.alcohol.ui.common.ToolbarMenuBottomSheetDialog

class RecordMenuBottomSheetDialog : ToolbarMenuBottomSheetDialog() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addList(0, getString(R.string.record_delete))
    }
}