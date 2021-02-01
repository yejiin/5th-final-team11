package com.doubleslas.fifith.alcohol.ui.auth.recommendinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.doubleslas.fifith.alcohol.databinding.FragmentRecommendInfoDrinkTypeBinding
import com.doubleslas.fifith.alcohol.ui.common.base.BaseFragment

class RecommendInfoDrinkTypeFragment : BaseFragment<FragmentRecommendInfoDrinkTypeBinding>() {
    private val viewModel by lazy { ViewModelProvider(activity!!).get(RecommendInfoViewModel::class.java) }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRecommendInfoDrinkTypeBinding {
        return FragmentRecommendInfoDrinkTypeBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.let { b ->
            b.layoutLiquor.setOnClickListener {
                viewModel.toggleCheckLiquor()
            }

            b.layoutWine.setOnClickListener {
                viewModel.toggleCheckWine()
            }

            b.layoutBeer.setOnClickListener {
                viewModel.toggleCheckBeer()
            }

            b.btnNext.setOnClickListener {
                val activity = activity as? RecommendInfoActivity
                activity?.openSecondPage()
            }
        }

        viewModel.checkLiquor.observe(viewLifecycleOwner, Observer {
            binding?.ivLiquor?.isSelected = it
            binding?.tvLiquor?.isSelected = it
            checkNextButton()
        })

        viewModel.checkWine.observe(viewLifecycleOwner, Observer {
            binding?.ivWine?.isSelected = it
            binding?.tvWine?.isSelected = it
            checkNextButton()
        })

        viewModel.checkBeer.observe(viewLifecycleOwner, Observer {
            binding?.ivBeer?.isSelected = it
            binding?.tvBeer?.isSelected = it
            checkNextButton()
        })

    }

    private fun checkNextButton() {
        val checked =
            (viewModel.checkBeer.value ?: false)
                    || (viewModel.checkLiquor.value ?: false)
                    || (viewModel.checkWine.value ?: false)

        binding?.btnNext?.isEnabled = checked
    }
}