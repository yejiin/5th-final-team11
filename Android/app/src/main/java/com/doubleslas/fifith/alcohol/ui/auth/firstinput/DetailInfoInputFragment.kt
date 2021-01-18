package com.doubleslas.fifith.alcohol.ui.auth.firstinput

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.doubleslas.fifith.alcohol.databinding.FragmentDetailInfoInputBinding
import com.doubleslas.fifith.alcohol.databinding.FragmentDrinkTypeBinding
import com.doubleslas.fifith.alcohol.ui.common.base.BaseFragment
import com.doubleslas.fifith.alcohol.viewmodel.FirstInfoViewModel

class DetailInfoInputFragment : BaseFragment<FragmentDetailInfoInputBinding>() {
    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailInfoInputBinding {
        return FragmentDetailInfoInputBinding.inflate(inflater, container, false)
    }

}