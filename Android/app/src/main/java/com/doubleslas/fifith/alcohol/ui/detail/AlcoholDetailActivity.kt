package com.doubleslas.fifith.alcohol.ui.detail

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.doubleslas.fifith.alcohol.R
import com.doubleslas.fifith.alcohol.databinding.ActivityAlcoholDetailBinding
import com.doubleslas.fifith.alcohol.viewmodel.DetailViewModel
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.activity_alcohol_detail.*

class AlcoholDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlcoholDetailBinding
    private val detailViewModel by lazy { DetailViewModel() }
    private val alcoholId by lazy { intent.getIntExtra(EXTRA_ALCOHOL_ID, 0) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlcoholDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val chip = Chip(this)
        val chip2 = Chip(this)
        val chip3 = Chip(this)
        val chip4 = Chip(this)
        val chip5 = Chip(this)
        val chip6 = Chip(this)
        val chip7 = Chip(this)
        val chip8 = Chip(this)
        val chip9 = Chip(this)
        val chip10 = Chip(this)
        val chip11 = Chip(this)
        val chip12 = Chip(this)
        val chip13 = Chip(this)

        chip.setText("Test")
        chip.isCheckable = false
        binding.chipGroup.addView(chip)
        chip2.setText("Test2")
        chip2.isCheckable = false
        binding.chipGroup.addView(chip2)
        chip3.setText("Test3")
        chip3.isCheckable = false
        binding.chipGroup.addView(chip3)
        chip4.setText("Test4")
        chip4.isCheckable = false
        binding.chipGroup.addView(chip4)
        chip5.setText("Test5")
        chip5.isCheckable = false
        binding.chipGroup.addView(chip5)
        chip6.setText("Test6")
        chip6.isCheckable = false
        binding.chipGroup.addView(chip6)
        chip7.setText("Test7")
        chip7.isCheckable = false
        binding.chipGroup.addView(chip7)


        chip8.setText("Test8")
        chip8.isCheckable = false
        binding.chipGroupTaste.addView(chip8)
        chip9.setText("Test7")
        chip9.isCheckable = false
        binding.chipGroupTaste.addView(chip9)
        chip10.setText("Test8")
        chip10.isCheckable = false
        binding.chipGroupTaste.addView(chip10)
        chip11.setText("Test7")
        chip11.isCheckable = false
        binding.chipGroupTaste.addView(chip11)
        chip12.setText("Test8")
        chip12.isCheckable = false
        binding.chipGroupTaste.addView(chip12)


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