package com.doubleslas.fifith.alcohol.ui.auth.recommendinfo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.doubleslas.fifith.alcohol.R
import com.doubleslas.fifith.alcohol.databinding.ChipRecommendInfoBinding
import com.doubleslas.fifith.alcohol.databinding.FragmentRecommendInfoDetailBinding
import com.doubleslas.fifith.alcohol.model.base.ApiStatus
import com.doubleslas.fifith.alcohol.ui.common.NumberInputBottomSheetDialog
import com.doubleslas.fifith.alcohol.ui.common.base.BaseFragment
import com.doubleslas.fifith.alcohol.ui.main.MainActivity
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.slider.RangeSlider


class RecommendInfoDetailFragment : BaseFragment<FragmentRecommendInfoDetailBinding>() {
    private val viewModel by lazy { ViewModelProvider(activity!!).get(RecommendInfoViewModel::class.java) }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRecommendInfoDetailBinding {
        return FragmentRecommendInfoDetailBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.let { b ->

            refreshAbv(b.rangeAbv)
            b.rangeAbv.addOnChangeListener { slider, value, fromUser ->
                refreshAbv(slider)
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



            b.layoutLiquorTypeContent.let { layout ->
                for (str in viewModel.getLiquorTypeList()) {
                    createChip(layout, str)
                }
            }

            b.layoutLiquorFlavorContent.let { layout ->
                for (str in viewModel.getLiquorFlavorList()) {
                    createChip(layout, str)
                }
            }

            b.layoutWineTypeContent.let { layout ->
                for (str in viewModel.getWineTypeList()) {
                    createChip(layout, str)
                }
            }

            b.tvPriceLow.text = viewModel.getMinPrice().toString()
            b.tvPriceHigh.text = viewModel.getMaxPrice().toString()

            b.layoutPriceLow.setOnClickListener {
                val min = viewModel.getMinPrice()
                val maxString = b.tvPriceHigh.text.toString()
                val max = if (maxString.isEmpty()) viewModel.getMaxPrice() else maxString.toInt()
                val dialog = NumberInputBottomSheetDialog().apply {
                    setRange(min, max, 1000)
                    setValue(b.tvPriceLow.text.toString().toInt())
                }
                dialog.show(fragmentManager!!, null) {
                    b.tvPriceLow.text = it.toString()
                }
            }

            b.layoutPriceHigh.setOnClickListener {
                val minString = b.tvPriceLow.text.toString()
                val min = if (minString.isEmpty()) viewModel.getMinPrice() else minString.toInt()
                val max = viewModel.getMaxPrice()
                val dialog = NumberInputBottomSheetDialog().apply {
                    setRange(min, max, 1000)
                    setValue(b.tvPriceHigh.text.toString().toInt())
                }

                dialog.show(fragmentManager!!, null) {
                    b.tvPriceHigh.text = it.toString()
                }
            }

            b.seekBarWineFlavor.tvLabel1.text = "Dry"
            b.seekBarWineFlavor.tvLabel2.text = "Sweet"

            b.seekBarWineBody.tvLabel1.text = "Light"
            b.seekBarWineBody.tvLabel2.text = "Heavy"

            b.layoutBeerTypeContent.let { layout ->
                val list = viewModel.getBeerTypeList()
                for (pair in list) {
                    val titleChip = createChip(layout, pair.first)


                    titleChip.layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        marginEnd =
                            resources.getDimension(R.dimen.first_input_default_padding).toInt()
                        marginStart =
                            resources.getDimension(R.dimen.first_input_default_padding).toInt()
                        topMargin =
                            resources.getDimension(R.dimen.first_input_beer_type_title_margin_top)
                                .toInt()
                        bottomMargin =
                            resources.getDimension(R.dimen.first_input_beer_type_title_margin_bottom)
                                .toInt()
                    }

                    if (pair.second.isEmpty()) continue


                    val group = ChipGroup(context).apply {
                        setBackgroundColor(
                            ResourcesCompat.getColor(resources, R.color.gray, null)
                        )
                        setPadding(
                            resources.getDimension(R.dimen.first_input_beer_type_margin_horizontal)
                                .toInt(),
                            resources.getDimension(R.dimen.first_input_beer_type_margin_vertical)
                                .toInt(),
                            resources.getDimension(R.dimen.first_input_beer_type_margin_horizontal)
                                .toInt(),
                            resources.getDimension(R.dimen.first_input_beer_type_margin_vertical)
                                .toInt()
                        )
                    }

                    layout.addView(group)
                    for (str in pair.second) {
                        createChip(group, str).setOnClickListener {
                            var ch = true
                            for (child in group.children) {
                                child as? Chip ?: continue
                                if (!child.isChecked) {
                                    ch = false
                                    break
                                }
                            }

                            titleChip.isChecked = ch
                        }
                    }

                    titleChip.setOnClickListener {
                        it as Chip
                        val checked = it.isChecked

                        for (child in group.children) {
                            (child as? Chip)?.isChecked = checked
                        }
                    }
                }
            }

            b.layoutBeerPlaceContent.let { layout ->
                for (str in viewModel.getBeerPlaceList()) {
                    createChip(layout, str)
                }
            }

            b.btnNext.setOnClickListener {
                submit()
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

        binding?.scrollview?.requestChildFocus(v, v)
    }

    private fun createChip(parent: ViewGroup, text: String): Chip {
        val chip = ChipRecommendInfoBinding.inflate(layoutInflater).root
        chip.text = text
        parent.addView(chip)

        return chip
    }

    private fun refreshAbv(slider: RangeSlider) {
        val text = "${slider.values[0].toInt()} 도 - ${slider.values[1].toInt()} 도"
        binding?.chipAbv?.text = text
    }

    private fun submit() {
        val binding = binding ?: return
        val data = viewModel.createModel(binding) ?: return
        viewModel.submit(data).observe(viewLifecycleOwner, Observer {
            when (it) {
                is ApiStatus.Success -> {
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }
            }
        })
    }
}