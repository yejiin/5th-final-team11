package com.doubleslas.fifith.alcohol.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.doubleslas.fifith.alcohol.App
import com.doubleslas.fifith.alcohol.R
import com.doubleslas.fifith.alcohol.databinding.ViewReviewDetailBinding
import com.doubleslas.fifith.alcohol.dto.ReviewDetailData
import java.text.DecimalFormat

class ReviewDetailView(context: Context, attrs: AttributeSet?, defStyle: Int) :
    FrameLayout(context, attrs, defStyle) {
    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private val binding: ViewReviewDetailBinding =
        ViewReviewDetailBinding.inflate(LayoutInflater.from(context), this, true)

    val tvDate = binding.tvDate
    val etPlace = binding.etPlace
    val etDrink = binding.etDrink
    val etPrice = binding.etPrice
    val seekBarHangover = binding.seekBarHangover

    init {
        seekBarHangover.seekBar.isEnabled = false
        seekBarHangover.tvLabel1.text = App.getString(R.string.hangover_none)
        seekBarHangover.tvLabel2.text = App.getString(R.string.hangover_heavy)
    }

    fun bind(data: ReviewDetailData) {

        setDetailText(tvDate, data.date)
        setDetailText(etPlace, data.place)
        setDetailText(etDrink, DecimalFormat("0.#").format(data.drink ?: 0))
        setDetailText(etPrice, data.price?.toString())

        if (data.hangover == null) {
            binding.layoutHangover.visibility = View.GONE
        } else {
            binding.layoutHangover.visibility = View.VISIBLE
            seekBarHangover.seekBar.progress = data.hangover
        }
    }

    fun setIndicator(value: Boolean) {
        tvDate.isEnabled = !value
        etDrink.isEnabled = !value
        etPlace.isEnabled = !value
        etPrice.isEnabled = !value
        seekBarHangover.seekBar.isEnabled = !value
    }


    private fun setDetailText(textView: TextView, text: String?) {
        if (text == null || text == "0") {
            (textView.parent as View).visibility = View.GONE
        } else {
            (textView.parent as View).visibility = View.VISIBLE
            textView.text = text
        }
    }

}