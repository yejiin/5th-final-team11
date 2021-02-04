package com.doubleslas.fifith.alcohol.ui.detail

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.doubleslas.fifith.alcohol.R
import com.doubleslas.fifith.alcohol.databinding.ActivityAlcoholDetailBinding
import com.doubleslas.fifith.alcohol.model.network.base.ApiStatus
import com.doubleslas.fifith.alcohol.ui.reivew.ReviewBottomSheetDialog
import com.doubleslas.fifith.alcohol.viewmodel.DetailViewModel
import com.doubleslas.fifith.alcohol.viewmodel.ReviewViewModel
import com.google.android.material.chip.Chip

class AlcoholDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlcoholDetailBinding
    private val detailViewModel by lazy { DetailViewModel() }
    private val reviewViewModel by lazy { ReviewViewModel() }
//    private val alcoholId by lazy { intent.getIntExtra(EXTRA_ALCOHOL_ID, 0) }

    private val adapter by lazy { DetailReviewAdapter() }
    private val viewPagerAdapter by lazy { DetailViewPagerAdapter(this) }
    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlcoholDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.rvDetailReview.adapter = adapter


        // 탭 레이아웃
        binding.viewpagerDetail.adapter = viewPagerAdapter


//        reviewViewModel.readReview(alcoholId, 0).observe(this, Observer {
//            when (it) {
//                is ApiStatus.Loading -> {
//
//                }
//                is ApiStatus.Success -> {
//                    adapter.setData(it.data.reviewList)
//                }
//                is ApiStatus.Error -> {
//
//                }
//
//            }
//        })
//        binding.btnWriteReview.setOnClickListener {
//            val bottomSheet =
//                ReviewBottomSheetDialog.create(alcoholId)
//
//            bottomSheet.onListener {
//                reviewViewModel.readReview(alcoholId, 0).observe(this, Observer {
//                    when (it) {
//                        is ApiStatus.Loading -> {
//
//                        }
//                        is ApiStatus.Success -> {
//                            adapter.setData(it.data.reviewList)
//                        }
//                        is ApiStatus.Error -> {
//
//                        }
//
//                    }
//                })
//            }
//            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
//        }


//        detailViewModel.getDetail(alcoholId).observe(this, Observer {
//            when (it) {
//                is ApiStatus.Loading -> {
//
//                }
//                is ApiStatus.Success -> {
//                    binding.tvPriceInfo.text =
//                        it.data.lowestPrice.toString() + "-" + it.data.highestPrice.toString() + " 원"
//                    binding.tvVolumeInfo.text =
//                        it.data.ml.toString() + " ml" + " / " + it.data.abv.toString() + " %"
//                    binding.detailRating.rating = it.data.starAvg
//                    binding.tvAlcoholName.text = it.data.name
//                    binding.tvDrinkName.text = it.data.name
//                    binding.tvDescription.text = it.data.description
//
//                    for (index in it.data.kind.indices) {
//                        val chip = Chip(binding.chipGroup.context)
//                        chip.background = ContextCompat.getDrawable(
//                            this,
//                            R.drawable.detail_button_active_background
//                        )
//                        chip.setTextColor(Color.parseColor("#FFFFFF"))
//                        chip.text = it.data.kind[index]
//                        chip.isClickable = false
//                        chip.isCheckable = false
//                        chip.chipBackgroundColor = ColorStateList.valueOf(
//                            ContextCompat.getColor(
//                                this,
//                                R.color.chipColor
//                            )
//                        )
//                        binding.chipGroup.addView(chip)
//                    }
//
//
//                    // 와인 파트
////                    if (it.data.country != null && it.data.area != null && it.data.flavor != null && it.data.body != null) {
////                        binding.tvNation.visibility = View.VISIBLE
////                        binding.tvNationInfo.visibility = View.VISIBLE
////                        binding.tvNationInfo.text = it.data.country + it.data.area
////                        binding.layoutBody.visibility = View.VISIBLE
////                        binding.seekBarFlavor.seekBar.progress = it.data.flavor
////                        binding.seekBarBody.seekBar.progress = it.data.body
////                        binding.seekBarBody.seekBar.isEnabled = false
////                        binding.seekBarFlavor.seekBar.isEnabled = false
////
////                        binding.seekBarFlavor.tvLabel1.text = "Dry"
////                        binding.seekBarFlavor.tvLabel2.text = "Sweet"
////
////                        binding.seekBarBody.tvLabel1.text = "Light"
////                        binding.seekBarBody.tvLabel2.text = "Heavy"
////                    } else {
////
////                        binding.layoutBody.visibility = View.GONE
////                        binding.seekBarFlavor.seekBar.visibility = View.GONE
////                        binding.seekBarFlavor.tvLabel1.visibility = View.GONE
////                        binding.seekBarFlavor.tvLabel2.visibility = View.GONE
////                    }
//
//
//                    // 맥주 파트
////                    if (it.data.areas != null) {
////                        binding.layoutKinds.visibility = View.VISIBLE
////                        binding.layoutFlavor.visibility = View.GONE
////                        binding.layoutBody.visibility = View.GONE
////                        binding.tvNation.visibility = View.VISIBLE
////                        binding.tvNationInfo.visibility = View.VISIBLE
////                        binding.tvNationInfo.text = it.data.areas.toString()
////                        for (index in it.data.areas.indices) {
////                            val chip = Chip(binding.chipGroupAreas.context)
////                            chip.setTextColor(Color.parseColor("#FFFFFF"))
////                            chip.text = it.data.areas[index]
////                            chip.isClickable = false
////                            chip.isCheckable = false
////                            chip.chipBackgroundColor = ColorStateList.valueOf(
////                                ContextCompat.getColor(
////                                    this,
////                                    R.color.chipColor
////                                )
////                            )
////                            binding.chipGroupAreas.addView(chip)
////                        }
////                    } else {
////                        binding.tvNation.visibility = View.GONE
////                        binding.tvNationInfo.visibility = View.GONE
////                        binding.layoutAreas.visibility = View.GONE
////                    }
//
//                    // 양주 파트
//                    if (it.data.flavors != null) {
//                        for (index in it.data.flavors.indices) {
//                            val chip = Chip(binding.chipGroupFlavor.context)
//                            chip.setTextColor(Color.parseColor("#FFFFFF"))
//                            chip.text = it.data.flavors[index]
//                            chip.isClickable = false
//                            chip.isCheckable = false
//                            chip.chipBackgroundColor = ColorStateList.valueOf(
//                                ContextCompat.getColor(
//                                    this,
//                                    R.color.chipColor
//                                )
//                            )
//                            binding.chipGroupFlavor.addView(chip)
//                        }
//                    }
//                }
//                is ApiStatus.Error -> {
//                }
//            }
//        })

//        binding.rvAlcoholSimilar.adapter = SimilarAdapter(this)
//
//        binding.rvAlcoholSimilar.layoutManager = LinearLayoutManager(this).also {
//            it.orientation = LinearLayoutManager.HORIZONTAL
//        }
//        binding.rvDetailReview.layoutManager = LinearLayoutManager(this).also {
//            it.orientation = LinearLayoutManager.VERTICAL
//        }
//
//        binding.rvAlcoholSimilar.addItemDecoration(DetailItemDecoration(5))
//        binding.rvDetailReview.addItemDecoration(DetailReviewDecoration(5))
//        binding.btnAlcoholInfo.setOnClickListener {
//            onInfoBtnClicked()
//        }
//
//        binding.btnAlcoholReview.setOnClickListener {
//            onReviewBtnClicked()
//        }
//    }
//
//    private fun onInfoBtnClicked() {
//        binding.layoutBasicInfo.visibility = View.VISIBLE
//        binding.layoutDescription.visibility = View.VISIBLE
//        binding.layoutInfo.visibility = View.VISIBLE
//        binding.layoutReview.visibility = View.GONE
//
//        binding.btnAlcoholReview.setTextColor(Color.parseColor("#707070"))
//        binding.btnAlcoholInfo.setTextColor(Color.parseColor("#FFFFFF"))
//
//        binding.btnAlcoholInfo.background =
//            ContextCompat.getDrawable(this, R.drawable.detail_button_active_background)
//        binding.btnAlcoholReview.background =
//            ContextCompat.getDrawable(this, R.drawable.detail_button_inactive_background)
//    }
//
//    private fun onReviewBtnClicked() {
//        binding.layoutBasicInfo.visibility = View.GONE
//        binding.layoutDescription.visibility = View.GONE
//        binding.layoutInfo.visibility = View.GONE
//        binding.layoutReview.visibility = View.VISIBLE
//        binding.btnAlcoholInfo.setTextColor(Color.parseColor("#707070"))
//        binding.btnAlcoholReview.setTextColor(Color.parseColor("#FFFFFF"))
//        binding.btnAlcoholInfo.background =
//            ContextCompat.getDrawable(this, R.drawable.detail_button_inactive_background)
//        binding.btnAlcoholReview.background =
//            ContextCompat.getDrawable(this, R.drawable.detail_button_active_background)
//    }

//    companion object {
//        private const val EXTRA_ALCOHOL_ID = "EXTRA_ALCOHOL_ID"
//        fun getStartIntent(context: Context, id: Int): Intent {
//
//            return Intent(context, AlcoholDetailActivity::class.java).apply {
//                putExtra(EXTRA_ALCOHOL_ID, id)
//            }
//        }
//    }

    }

}