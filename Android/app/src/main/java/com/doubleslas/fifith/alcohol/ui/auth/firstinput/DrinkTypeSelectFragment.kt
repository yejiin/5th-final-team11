package com.doubleslas.fifith.alcohol.ui.auth.firstinput

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.doubleslas.fifith.alcohol.databinding.FragmentDrinkTypeBinding
import com.doubleslas.fifith.alcohol.ui.common.base.BaseFragment
import com.doubleslas.fifith.alcohol.viewmodel.FirstInfoViewModel

class DrinkTypeSelectFragment : BaseFragment<FragmentDrinkTypeBinding>() {
    private val viewModel by lazy { ViewModelProvider(activity!!).get(FirstInfoViewModel::class.java) }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDrinkTypeBinding {
        return FragmentDrinkTypeBinding.inflate(inflater, container, false)
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
                val activity = activity as? FirstInputActivity
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