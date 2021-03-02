package com.doubleslas.fifith.alcohol.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.Glide
import com.doubleslas.fifith.alcohol.databinding.ActivityAlcoholDetailBinding
import com.doubleslas.fifith.alcohol.model.base.ApiStatus
import com.google.android.material.tabs.TabLayoutMediator

class AlcoholDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlcoholDetailBinding
    private val detailViewModel by lazy {
        ViewModelProvider(
            this, DetailViewModel.Factory(alcoholId)
        ).get(DetailViewModel::class.java)
    }
    private val alcoholId by lazy { intent.getIntExtra(EXTRA_ALCOHOL_ID, 0) }

    private val viewPagerAdapter by lazy { DetailViewPagerAdapter(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlcoholDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // viewpager2
        binding.viewpagerDetail.adapter = viewPagerAdapter

        val tabLayoutTextArray = arrayOf("정보", "리뷰")
        TabLayoutMediator(binding.tablayoutDetail, binding.viewpagerDetail) { tab, position ->
            tab.text = tabLayoutTextArray[position]
        }.attach()

        detailViewModel.infoLiveData.observe(this, Observer {
            when (it) {
                is ApiStatus.Loading -> {

                }
                is ApiStatus.Success -> {
                    Glide.with(this)
                        .load(it.data.image)
                        .into(binding.ivAlcoholDetail)

                    binding.detailRating.rating = it.data.starAvg
                    binding.tvAlcoholName.text = it.data.name
                    binding.tvDrinkName.text = it.data.name
                }
                is ApiStatus.Error -> {
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        detailViewModel.getDetail()
    }

    class DetailViewPagerAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {

        private var fragmentList = listOf(DetailInfoFragment(), DetailReviewFragment())
        override fun getItemCount(): Int {
            return fragmentList.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragmentList[position]
        }
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