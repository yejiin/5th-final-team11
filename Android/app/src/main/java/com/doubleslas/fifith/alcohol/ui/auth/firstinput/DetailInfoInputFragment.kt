package com.doubleslas.fifith.alcohol.ui.auth.firstinput

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.doubleslas.fifith.alcohol.databinding.FragmentDetailInfoInputBinding
import com.doubleslas.fifith.alcohol.ui.common.base.BaseFragment
import com.doubleslas.fifith.alcohol.viewmodel.FirstInfoViewModel
import kotlinx.android.synthetic.main.fragment_detail_info_input.*


class DetailInfoInputFragment : BaseFragment<FragmentDetailInfoInputBinding>() {
    private val viewModel by lazy { ViewModelProvider(activity!!).get(FirstInfoViewModel::class.java) }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailInfoInputBinding {
        return FragmentDetailInfoInputBinding.inflate(inflater, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.let { b ->
            b.rangeAbv.addOnChangeListener { slider, value, fromUser ->
                val text = "${slider.values[0].toInt()} 도 - ${slider.values[1].toInt()} 도"
                b.chipAbv.text = text
            }

            b.layoutLiquorLabel.setOnClickListener {
                toggleVisibility(b.layoutLiquorContent)
            }
            b.layoutBeerLabel.setOnClickListener {
                toggleVisibility(b.layoutBeerContent)
            }
            b.layoutWineLabel.setOnClickListener {
                toggleVisibility(b.layoutWineContent)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        binding?.layoutLiquor?.visibility =
            if (viewModel.checkLiquor.value == true) View.VISIBLE else View.GONE

        binding?.layoutBeer?.visibility =
            if (viewModel.checkBeer.value == true) View.VISIBLE else View.GONE

        binding?.layoutWine?.visibility =
            if (viewModel.checkWine.value == true) View.VISIBLE else View.GONE

    }

    private fun toggleVisibility(v: View) {
        v.isVisible = !v.isVisible

        scrollview.requestChildFocus(v, v)

//        val vTop = v.top
//        val vBottom = v.bottom
//        val sHeight: Int = scrollview.bottom
//        scrollview.smoothScrollTo((vTop + vBottom - sHeight) / 2, 0)
    }
}