package com.doubleslas.fifith.alcohol.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.doubleslas.fifith.alcohol.databinding.ActivityAlcoholDetailBinding
import com.doubleslas.fifith.alcohol.model.network.base.ApiStatus
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
                }
                is ApiStatus.Error -> {

                }
            }
        })
        binding.rvAlcoholSimilar.adapter = AlcoholDetailAdapter(this)

        binding.rvAlcoholSimilar.layoutManager = LinearLayoutManager(this).also {
            it.orientation = LinearLayoutManager.HORIZONTAL
        }

        binding.rvAlcoholSimilar.addItemDecoration(DetailItemDecoration(5))
        binding.btnAlcoholInfo.setOnClickListener {
            onInfoBtnClicked()
        }

        binding.btnAlcoholReview.setOnClickListener {

        }
    }

    private fun onInfoBtnClicked() {

    }

    private fun onReviewBtnClicked() {

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