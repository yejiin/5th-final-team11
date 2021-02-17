package com.doubleslas.fifith.alcohol.ui.record.cupboard

import android.view.LayoutInflater
import android.view.ViewGroup
import com.doubleslas.fifith.alcohol.databinding.FragmentCupboardBinding
import com.doubleslas.fifith.alcohol.ui.common.base.BaseFragment

class CupboardFragment : BaseFragment<FragmentCupboardBinding>() {
    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCupboardBinding {
        return FragmentCupboardBinding.inflate(inflater, container, false)
    }
}