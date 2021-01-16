package com.doubleslas.fifith.alcohol.ui.detail

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.doubleslas.fifith.alcohol.R
import com.doubleslas.fifith.alcohol.databinding.ActivityAlcoholDetailBinding
import com.doubleslas.fifith.alcohol.model.network.base.ApiStatus
import com.doubleslas.fifith.alcohol.ui.common.base.CalendarDialogFragment
import com.doubleslas.fifith.alcohol.ui.common.base.ReviewBottomSheetDialog
import com.doubleslas.fifith.alcohol.viewmodel.DetailViewModel
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.layout_write_review.*

class AlcoholDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlcoholDetailBinding
    private val detailViewModel by lazy { DetailViewModel() }
    private val alcoholId by lazy { intent.getIntExtra(EXTRA_ALCOHOL_ID, 0) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlcoholDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.btnWriteReview.setOnClickListener {
            val bottomSheet = ReviewBottomSheetDialog()

            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }

        detailViewModel.getDetail(10).observe(this, Observer {
            when (it) {
                is ApiStatus.Loading -> {

                }
                is ApiStatus.Success -> {
                    binding.tvPriceInfo.text =
                        it.data.lowestPrice.toString() + "-" + it.data.highestPrice.toString() + " 원"
                    binding.tvVolumeInfo.text =
                        it.data.ml.toString() + " ml" + " / " + it.data.abv.toString() + " %"
                    binding.detailRating.rating = it.data.starAvg
                    binding.tvAlcoholName.text = it.data.name
                    binding.tvDrinkName.text = it.data.name
                    binding.tvDescription.text = it.data.description

                    for (index in it.data.kind.indices) {
                        val chip = Chip(binding.chipGroup.context)
                        chip.background = ContextCompat.getDrawable(
                            this,
                            R.drawable.detail_button_active_background
                        )
                        chip.setTextColor(Color.parseColor("#FFFFFF"))
                        chip.text = it.data.kind[index]
                        chip.isClickable = false
                        chip.isCheckable = false
                        chip.chipBackgroundColor = ColorStateList.valueOf(
                            ContextCompat.getColor(
                                this,
                                R.color.chipColor
                            )
                        )
                        binding.chipGroup.addView(chip)
                    }


                    if (it.data.country != null && it.data.area != null && it.data.wineKind != null && it.data.flavor != null && it.data.body != null) {
                        binding.tvNation.visibility = View.VISIBLE
                        binding.tvNationInfo.visibility = View.VISIBLE
                        binding.tvNationInfo.text = it.data.country + it.data.area
                        binding.sbTaste.progress = it.data.flavor
                        binding.sbBody.progress = it.data.body
                    } else {

                        binding.clBody.visibility = View.GONE
                        binding.sbTaste.visibility = View.GONE
                        binding.tvDry.visibility = View.GONE
                        binding.tvSweet.visibility = View.GONE
                    }

                    if (it.data.areas != null) {
                        binding.tvNation.visibility = View.VISIBLE
                        binding.tvNationInfo.visibility = View.VISIBLE
                        binding.tvNationInfo.text = it.data.areas.toString()
                    } else {
                        binding.tvNation.visibility = View.GONE
                        binding.tvNationInfo.visibility = View.GONE
                    }

                    if (it.data.flavors != null) {
                        for (index in it.data.flavors.indices) {
                            val chip = Chip(binding.chipGroupTaste.context)
                            chip.setTextColor(Color.parseColor("#FFFFFF"))
                            chip.text = it.data.flavors[index]
                            chip.isClickable = false
                            chip.isCheckable = false
                            chip.chipBackgroundColor = ColorStateList.valueOf(
                                ContextCompat.getColor(
                                    this,
                                    R.color.chipColor
                                )
                            )
                            binding.chipGroupTaste.addView(chip)
                        }
                    }
                }
                is ApiStatus.Error -> {
                }
            }
        })
        binding.rvAlcoholSimilar.adapter = AlcoholDetailAdapter(this)
        binding.rvDetailReview.adapter = DetailReviewAdapter(this)

        binding.rvAlcoholSimilar.layoutManager = LinearLayoutManager(this).also {
            it.orientation = LinearLayoutManager.HORIZONTAL
        }
        binding.rvDetailReview.layoutManager = LinearLayoutManager(this).also {
            it.orientation = LinearLayoutManager.VERTICAL
        }

        binding.rvAlcoholSimilar.addItemDecoration(DetailItemDecoration(5))
        binding.rvDetailReview.addItemDecoration(DetailReviewDecoration(5))
        binding.btnAlcoholInfo.setOnClickListener {
            onInfoBtnClicked()
        }

        binding.btnAlcoholReview.setOnClickListener {
            onReviewBtnClicked()
        }
    }

    private fun onInfoBtnClicked() {
        binding.clBasicInfo.visibility = View.VISIBLE
        binding.clDescription.visibility = View.VISIBLE
        binding.layoutInfo.visibility = View.VISIBLE
        binding.layoutReview.visibility = View.GONE

        binding.btnAlcoholReview.setTextColor(Color.parseColor("#707070"))
        binding.btnAlcoholInfo.setTextColor(Color.parseColor("#FFFFFF"))

        binding.btnAlcoholInfo.background =
            ContextCompat.getDrawable(this, R.drawable.detail_button_active_background)
        binding.btnAlcoholReview.background =
            ContextCompat.getDrawable(this, R.drawable.detail_button_inactive_background)
    }

    private fun onReviewBtnClicked() {
        binding.clBasicInfo.visibility = View.GONE
        binding.clDescription.visibility = View.GONE
        binding.layoutInfo.visibility = View.GONE
        binding.layoutReview.visibility = View.VISIBLE
        binding.btnAlcoholInfo.setTextColor(Color.parseColor("#707070"))
        binding.btnAlcoholReview.setTextColor(Color.parseColor("#FFFFFF"))
        binding.btnAlcoholInfo.background =
            ContextCompat.getDrawable(this, R.drawable.detail_button_inactive_background)
        binding.btnAlcoholReview.background =
            ContextCompat.getDrawable(this, R.drawable.detail_button_active_background)
    }

    companion object {
        private const val EXTRA_ALCOHOL_ID = "EXTRA_ALCOHOL_ID"
        fun getStartIntent(context: Context, id: Int): Intent {

            return Intent(context, AlcoholDetailActivity::class.java).apply {
                putExtra(EXTRA_ALCOHOL_ID, id)
            }
        }
    }

}