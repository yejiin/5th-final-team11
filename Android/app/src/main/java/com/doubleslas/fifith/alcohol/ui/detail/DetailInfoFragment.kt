package com.doubleslas.fifith.alcohol.ui.detail

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.doubleslas.fifith.alcohol.R
import com.doubleslas.fifith.alcohol.databinding.FragmentDetailInfoBinding
import com.doubleslas.fifith.alcohol.dto.DetailData
import com.doubleslas.fifith.alcohol.model.base.ApiStatus
import com.doubleslas.fifith.alcohol.ui.common.base.BaseFragment
import com.google.android.material.chip.Chip


class DetailInfoFragment : BaseFragment<FragmentDetailInfoBinding>() {
    private val detailViewModel by lazy { ViewModelProvider(activity!!).get(DetailViewModel::class.java) }


    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailInfoBinding {
        return FragmentDetailInfoBinding.inflate(inflater, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val chip = Chip(activity?.applicationContext)

        binding?.let { b ->
            b.rvAlcoholSimilar.adapter = SimilarAdapter(AlcoholDetailActivity())

            b.rvAlcoholSimilar.layoutManager = LinearLayoutManager(AlcoholDetailActivity()).also {
                it.orientation = LinearLayoutManager.HORIZONTAL
            }

            b.rvAlcoholSimilar.addItemDecoration(DetailItemDecoration(5))
        }


        detailViewModel.infoLiveData.observe(this, Observer {
            when (it) {
                is ApiStatus.Loading -> {

                }
                is ApiStatus.Success -> {
                    notifyDataChanged(it.data)
                }
                is ApiStatus.Error -> {

                }
            }
        })
    }

    private fun notifyDataChanged(data: DetailData) {

        binding?.let { b ->
            b.tvPriceInfo.text =
                data.lowestPrice.toString() + "-" + data.highestPrice.toString() + " 원"
            b.tvVolumeInfo.text =
                data.ml.toString() + " ml" + " / " + data.abv.toString() + " %"

            b.tvDescription.text = data.description

//                    for (index in data.kind.indices) {
//
////                        chip.background = ContextCompat.getDrawable(
////                            activity!!,
////                            R.drawable.detail_button_active_background
////
////                        )
//                        chip.setTextColor(Color.parseColor("#FFFFFF"))
//                        chip.text = data.kind[index]
//                        chip.isClickable = false
//                        chip.isCheckable = false
////                        chip.chipBackgroundColor = ColorStateList.valueOf(
////                            ContextCompat.getColor(
////                                activity!!,
////                                R.color.chipColor
////                            )
////                        )
//                        b.chipGroup.addView(chip)
//
//
//                    }


            // 와인 파트
            if (data.country != null && data.area != null && data.flavor != null && data.body != null) {
                b.tvNation.visibility = View.VISIBLE
                b.tvNationInfo.visibility = View.VISIBLE
                b.tvNationInfo.text = data.country + data.area
                b.layoutBody.visibility = View.VISIBLE
                b.seekBarFlavor.seekBar.progress = data.flavor
                b.seekBarBody.seekBar.progress = data.body
                b.seekBarBody.seekBar.isEnabled = false
                b.seekBarFlavor.seekBar.isEnabled = false

                b.seekBarFlavor.tvLabel1.text = "Dry"
                b.seekBarFlavor.tvLabel2.text = "Sweet"

                b.seekBarBody.tvLabel1.text = "Light"
                b.seekBarBody.tvLabel2.text = "Heavy"
            } else {

                b.layoutBody.visibility = View.GONE
                b.seekBarFlavor.seekBar.visibility = View.GONE
                b.seekBarFlavor.tvLabel1.visibility = View.GONE
                b.seekBarFlavor.tvLabel2.visibility = View.GONE
            }


//                    // 맥주 파트
            if (data.areas != null) {
                b.layoutKinds.visibility = View.VISIBLE
                b.layoutFlavor.visibility = View.GONE
                b.layoutBody.visibility = View.GONE
                b.tvNation.visibility = View.VISIBLE
                b.tvNationInfo.visibility = View.VISIBLE
                b.tvNationInfo.text = data.areas.toString()
//                        for (index in data.areas.indices) {
//                            val chip = Chip(b.chipGroupAreas.context)
//                            chip.setTextColor(Color.parseColor("#FFFFFF"))
//                            chip.text = data.areas[index]
//                            chip.isClickable = false
//                            chip.isCheckable = false
//                            chip.chipBackgroundColor = ColorStateList.valueOf(
//                                ContextCompat.getColor(
//                                    AlcoholDetailActivity(),
//                                    R.color.chipColor
//                                )
//                            )
//                            b.chipGroupAreas.addView(chip)
//                        }
            } else {
                b.tvNation.visibility = View.GONE
                b.tvNationInfo.visibility = View.GONE
                b.layoutAreas.visibility = View.GONE
            }

            // 양주 파트
            if (data.flavors != null) {
                for (index in data.flavors.indices) {
                    val chip = Chip(b.chipGroupFlavor.context)
                    chip.setTextColor(Color.parseColor("#FFFFFF"))
                    chip.text = data.flavors[index]
                    chip.isClickable = false
                    chip.isCheckable = false
                    chip.chipBackgroundColor = ColorStateList.valueOf(
                        ContextCompat.getColor(
                            AlcoholDetailActivity(),
                            R.color.chipColor
                        )
                    )
                    b.chipGroupFlavor.addView(chip)
                }
            }
        }
    }

}