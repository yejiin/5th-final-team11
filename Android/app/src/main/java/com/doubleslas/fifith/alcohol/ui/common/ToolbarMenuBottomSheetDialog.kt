package com.doubleslas.fifith.alcohol.ui.common

import android.os.Bundle
import android.view.View
import com.doubleslas.fifith.alcohol.R
import com.doubleslas.fifith.alcohol.ui.common.BottomSheetMenu

class ToolbarMenuBottomSheetDialog : BottomSheetMenu() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setList(listOf(getString(R.string.all_licence)))
    }
}