package com.doubleslas.fifith.alcohol.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.doubleslas.fifith.alcohol.databinding.ActivityAlcoholDetailBinding
import com.doubleslas.fifith.alcohol.model.network.base.ApiStatus
import com.doubleslas.fifith.alcohol.viewmodel.DetailViewModel
import com.google.android.material.tabs.TabLayoutMediator

class AlcoholDetailActivity : AppCompatActivity() {
    val tabLayoutTextArray = arrayOf("정보", "리뷰")
    private lateinit var binding: ActivityAlcoholDetailBinding
    private val detailViewModel by lazy { DetailViewModel() }
    private val alcoholId by lazy { intent.getIntExtra(EXTRA_ALCOHOL_ID, 0) }

    private val viewPagerAdapter by lazy { DetailViewPagerAdapter(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlcoholDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // viewpager2
        binding.viewpagerDetail.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tablayoutDetail, binding.viewpagerDetail) { tab, position ->
            tab.text = tabLayoutTextArray[position]
        }.attach()





        detailViewModel.getDetail(6).observe(this, Observer {
            when (it) {
                is ApiStatus.Loading -> {

                }
                is ApiStatus.Success -> {
                    binding.detailRating.rating = it.data.starAvg
                    binding.tvAlcoholName.text = it.data.name
                    binding.tvDrinkName.text = it.data.name
                }
                is ApiStatus.Error -> {
                }
            }
        })


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