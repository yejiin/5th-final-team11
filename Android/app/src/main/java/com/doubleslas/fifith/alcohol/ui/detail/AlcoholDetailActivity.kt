package com.doubleslas.fifith.alcohol.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.doubleslas.fifith.alcohol.databinding.ActivityAlcoholDetailBinding
import com.doubleslas.fifith.alcohol.model.network.base.ApiStatus
import com.doubleslas.fifith.alcohol.utils.LogUtil
import com.doubleslas.fifith.alcohol.viewmodel.DetailViewModel

class AlcoholDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlcoholDetailBinding
    private val detailViewModel by lazy { DetailViewModel() }
    private val alcoholId by lazy { intent.getIntExtra(EXTRA_ALCOHOL_ID, 0) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlcoholDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailViewModel.getDetail(6).observe(this, Observer {
            when (it) {
                is ApiStatus.Loading -> {

                }
                is ApiStatus.Success -> {
                    binding.tvPriceInfo.text = it.data.lowestPrice.toString() + "-" + it.data.highestPrice.toString() + " 원"
                    binding.tvVolumeInfo.text = it.data.ml.toString() + " ml" + " / " + it.data.abv.toString() + " %"
                    binding.detailRating.rating = it.data.starAvg
                    binding.tvAlcoholName.text = it.data.name
                    binding.tvDrinkName.text = it.data.name

                    if (it.data.country != null && it.data.area != null && it.data.town != null && it.data.wineKind != null && it.data.flavor != null && it.data.body != null) {
                        binding.tvNation.visibility = View.VISIBLE
                        binding.tvNationInfo.visibility = View.VISIBLE
                        binding.tvBreed.visibility = View.VISIBLE
                        binding.tvBreedInfo.visibility = View.VISIBLE
                        binding.tvNationInfo.text = it.data.country + it.data.area + it.data.town
                        binding.tvBreedInfo.text = it.data.wineKind
                        binding.sbTaste.progress = it.data.flavor
                        binding.sbBody.progress = it.data.body
                    } else {
                        binding.tvNation.visibility = View.GONE
                        binding.tvNationInfo.visibility = View.GONE
                        binding.clBody.visibility = View.GONE
                        binding.sbTaste.visibility = View.GONE
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
    }

    private fun onReviewBtnClicked() {
        binding.clBasicInfo.visibility = View.GONE
        binding.clDescription.visibility = View.GONE
        binding.layoutInfo.visibility = View.GONE
        binding.layoutReview.visibility = View.VISIBLE
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