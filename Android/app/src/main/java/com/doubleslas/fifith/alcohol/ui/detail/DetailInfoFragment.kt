package com.doubleslas.fifith.alcohol.ui.detail

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.doubleslas.fifith.alcohol.R
import com.doubleslas.fifith.alcohol.databinding.FragmentDetailInfoBinding
import com.doubleslas.fifith.alcohol.model.network.base.ApiStatus
import com.doubleslas.fifith.alcohol.viewmodel.DetailViewModel
import com.google.android.material.chip.Chip


class DetailInfoFragment : Fragment() {
    private lateinit var binding: FragmentDetailInfoBinding
    private val detailViewModel by lazy { DetailViewModel() }
//    private val alcoholId by lazy { intent.getIntExtra(EXTRA_ALCOHOL_ID, 0) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailInfoBinding.inflate(inflater, container, false)

        return binding.root
    }


//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        mContext = context
//
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val chip = Chip(activity?.applicationContext)

        binding.rvAlcoholSimilar.adapter = SimilarAdapter(AlcoholDetailActivity())

        binding.rvAlcoholSimilar.layoutManager = LinearLayoutManager(AlcoholDetailActivity()).also {
            it.orientation = LinearLayoutManager.HORIZONTAL
        }


        binding.rvAlcoholSimilar.addItemDecoration(DetailItemDecoration(5))

        detailViewModel.getDetail(148).observe(this, Observer {
            when (it) {
                is ApiStatus.Loading -> {

                }
                is ApiStatus.Success -> {
                    binding.tvPriceInfo.text =
                        it.data.lowestPrice.toString() + "-" + it.data.highestPrice.toString() + " 원"
                    binding.tvVolumeInfo.text =
                        it.data.ml.toString() + " ml" + " / " + it.data.abv.toString() + " %"

                    binding.tvDescription.text = it.data.description
//                    for (index in it.data.kind.indices) {
//
////                        chip.background = ContextCompat.getDrawable(
////                            activity!!,
////                            R.drawable.detail_button_active_background
////
////                        )
//                        chip.setTextColor(Color.parseColor("#FFFFFF"))
//                        chip.text = it.data.kind[index]
//                        chip.isClickable = false
//                        chip.isCheckable = false
////                        chip.chipBackgroundColor = ColorStateList.valueOf(
////                            ContextCompat.getColor(
////                                activity!!,
////                                R.color.chipColor
////                            )
////                        )
//                        binding.chipGroup.addView(chip)
//
//
//                    }


                    // 와인 파트
                    if (it.data.country != null && it.data.area != null && it.data.flavor != null && it.data.body != null) {
                        binding.tvNation.visibility = View.VISIBLE
                        binding.tvNationInfo.visibility = View.VISIBLE
                        binding.tvNationInfo.text = it.data.country + it.data.area
                        binding.layoutBody.visibility = View.VISIBLE
                        binding.seekBarFlavor.seekBar.progress = it.data.flavor
                        binding.seekBarBody.seekBar.progress = it.data.body
                        binding.seekBarBody.seekBar.isEnabled = false
                        binding.seekBarFlavor.seekBar.isEnabled = false

                        binding.seekBarFlavor.tvLabel1.text = "Dry"
                        binding.seekBarFlavor.tvLabel2.text = "Sweet"

                        binding.seekBarBody.tvLabel1.text = "Light"
                        binding.seekBarBody.tvLabel2.text = "Heavy"
                    } else {

                        binding.layoutBody.visibility = View.GONE
                        binding.seekBarFlavor.seekBar.visibility = View.GONE
                        binding.seekBarFlavor.tvLabel1.visibility = View.GONE
                        binding.seekBarFlavor.tvLabel2.visibility = View.GONE
                    }


//                    // 맥주 파트
                    if (it.data.areas != null) {
                        binding.layoutKinds.visibility = View.VISIBLE
                        binding.layoutFlavor.visibility = View.GONE
                        binding.layoutBody.visibility = View.GONE
                        binding.tvNation.visibility = View.VISIBLE
                        binding.tvNationInfo.visibility = View.VISIBLE
                        binding.tvNationInfo.text = it.data.areas.toString()
//                        for (index in it.data.areas.indices) {
//                            val chip = Chip(binding.chipGroupAreas.context)
//                            chip.setTextColor(Color.parseColor("#FFFFFF"))
//                            chip.text = it.data.areas[index]
//                            chip.isClickable = false
//                            chip.isCheckable = false
//                            chip.chipBackgroundColor = ColorStateList.valueOf(
//                                ContextCompat.getColor(
//                                    AlcoholDetailActivity(),
//                                    R.color.chipColor
//                                )
//                            )
//                            binding.chipGroupAreas.addView(chip)
//                        }
                    } else {
                        binding.tvNation.visibility = View.GONE
                        binding.tvNationInfo.visibility = View.GONE
                        binding.layoutAreas.visibility = View.GONE
                    }

                    // 양주 파트
                    if (it.data.flavors != null) {
                        for (index in it.data.flavors.indices) {
                            val chip = Chip(binding.chipGroupFlavor.context)
                            chip.setTextColor(Color.parseColor("#FFFFFF"))
                            chip.text = it.data.flavors[index]
                            chip.isClickable = false
                            chip.isCheckable = false
                            chip.chipBackgroundColor = ColorStateList.valueOf(
                                ContextCompat.getColor(
                                    AlcoholDetailActivity(),
                                    R.color.chipColor
                                )
                            )
                            binding.chipGroupFlavor.addView(chip)
                        }
                    }
                }
                is ApiStatus.Error -> {

                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        val alcoholDetailActivity: AlcoholDetailActivity = AlcoholDetailActivity()

        alcoholDetailActivity.refresh()
    }
}