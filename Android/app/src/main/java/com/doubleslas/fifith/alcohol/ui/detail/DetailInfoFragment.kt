package com.doubleslas.fifith.alcohol.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.doubleslas.fifith.alcohol.databinding.ChipRecommendInfoBinding
import com.doubleslas.fifith.alcohol.databinding.FragmentDetailInfoBinding
import com.doubleslas.fifith.alcohol.dto.DetailData
import com.doubleslas.fifith.alcohol.model.base.ApiStatus
import com.doubleslas.fifith.alcohol.ui.common.base.BaseFragment
import com.google.android.material.chip.Chip


class DetailInfoFragment : BaseFragment<FragmentDetailInfoBinding>() {
    private val detailViewModel by lazy { ViewModelProvider(activity!!).get(DetailViewModel::class.java) }
    private val adapter by lazy { SimilarAdapter() }

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailInfoBinding {
        return FragmentDetailInfoBinding.inflate(inflater, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.let { b ->
            b.rvAlcoholSimilar.adapter = adapter
            b.rvAlcoholSimilar.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

            b.tvPrice.setOnClickListener {
                b.tooltipPrice.isVisible = !b.tooltipPrice.isVisible
            }

            b.tooltipPrice.setOnClickListener {
                b.tooltipPrice.isVisible = false
            }

            b.root.setOnClickListener {
                b.tooltipPrice.isVisible = false
            }

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
        adapter.setData(data.similar)
        adapter.notifyDataSetChanged()

        binding?.let { b ->
            b.tvPriceInfo.text =
                data.lowestPrice.toString() + "-" + data.highestPrice.toString() + " 원"
            b.tvVolumeInfo.text =
                data.ml.toString() + " ml" + " / " + data.abv.toString() + " %"
            b.tvDescription.text = data.description

            b.tvReference.text = data.source

            if (data.country != null) { // 나라
                b.tvNation.visibility = View.VISIBLE
                b.tvNationInfo.visibility = View.VISIBLE

                b.tvNationInfo.text = data.country
            } else {
                b.tvNation.visibility = View.GONE
                b.tvNationInfo.visibility = View.GONE
            }

            b.chipGroupKinds.removeAllViews()
//            for (str in data.kind) {
//                createChip(b.chipGroupKinds, str)
//            }
            createChip(b.chipGroupKinds, data.kind)


            // 와인 파트
            if (data.subKind != null) { // 품종
                b.tvRace.visibility = View.VISIBLE
                b.tvRaceInfo.visibility = View.VISIBLE
                b.tvRace.text = data.subKind
            } else {
                b.tvRace.visibility = View.GONE
                b.tvRaceInfo.visibility = View.GONE
            }

            if (data.flavor != null) { // 맛
                b.layoutFlavor.visibility = View.VISIBLE
                b.seekBarFlavor.root.visibility = View.VISIBLE
                b.chipGroupFlavor.visibility = View.GONE

                b.seekBarFlavor.seekBar.progress = data.flavor
                b.seekBarFlavor.seekBar.isEnabled = false
                b.seekBarFlavor.tvLabel1.text = "Dry"
                b.seekBarFlavor.tvLabel2.text = "Sweet"
            } else {
                b.layoutFlavor.visibility = View.GONE
            }

            if (data.body != null) { // 바디감
                b.layoutBody.visibility = View.VISIBLE

                b.seekBarBody.seekBar.progress = data.body
                b.seekBarBody.seekBar.isEnabled = false
                b.seekBarBody.tvLabel1.text = "Light"
                b.seekBarBody.tvLabel2.text = "Heavy"
            } else {
                b.layoutBody.visibility = View.GONE
            }

            // 양주 파트
            if (data.flavors != null) {
                b.layoutFlavor.visibility = View.VISIBLE
                b.chipGroupFlavor.visibility = View.VISIBLE
                b.seekBarFlavor.root.visibility = View.GONE

                b.chipGroupFlavor.removeAllViews()
                for (str in data.flavors) {
                    createChip(b.chipGroupFlavor, str)
                }
            } else {
                b.layoutFlavor.visibility = View.GONE
            }

            if (data.userDrink != null) {
                b.tvAbvInfo.visibility = View.VISIBLE
                b.tvAbvInfo.text = data.userDrink
            } else {
                b.tvAbvInfo.visibility = View.GONE
            }
        }
    }


    private fun createChip(parent: ViewGroup, text: String): Chip {
        val chip = ChipRecommendInfoBinding.inflate(layoutInflater).root
        chip.text = text
        parent.addView(chip)

        chip.isChecked = true
        chip.isCheckable = false
        chip.isClickable = false

        return chip
    }

}