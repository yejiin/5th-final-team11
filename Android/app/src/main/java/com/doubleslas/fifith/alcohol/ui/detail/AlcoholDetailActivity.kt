package com.doubleslas.fifith.alcohol.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.doubleslas.fifith.alcohol.databinding.ActivityAlcoholDetailBinding

class AlcoholDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlcoholDetailBinding
    private val alcoholId by lazy { intent.getIntExtra(EXTRA_ALCOHOL_ID, 0) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlcoholDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.rvAlcoholSimilar.adapter = AlcoholDetailAdapter(this)

        binding.rvAlcoholSimilar.layoutManager = LinearLayoutManager(this).also {
            it.orientation = LinearLayoutManager.HORIZONTAL
        }

        binding.rvAlcoholSimilar.addItemDecoration(DetailItemDecoration(5))
        binding.btnAlcoholInfo.setOnClickListener {

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