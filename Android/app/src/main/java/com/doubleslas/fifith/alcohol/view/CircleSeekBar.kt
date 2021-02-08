package com.doubleslas.fifith.alcohol.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.core.content.res.ResourcesCompat
import com.doubleslas.fifith.alcohol.R

class CircleSeekBar(context: Context, attrs: AttributeSet?, defStyle: Int) :
    AppCompatSeekBar(context, attrs, defStyle) {

    private val stickHeight by lazy {
        resources.getDimension(R.dimen.circle_seekBar_height)
    }
    private val circleRadius by lazy {
        resources.getDimension(R.dimen.circle_seekBar_radius)
    }

    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var onSeekBarChangeListener: OnSeekBarChangeListener? = null

    private val backgroundPaint = Paint().apply {
        color = ResourcesCompat.getColor(resources, R.color.light_gray, null)
    }
    private val fillPaint = Paint().apply {
        color = ResourcesCompat.getColor(resources, R.color.orange, null)
    }

    override fun setOnSeekBarChangeListener(listener: OnSeekBarChangeListener?) {
        this.onSeekBarChangeListener = listener
    }

    override fun onDraw(canvas: Canvas) {

        val calcWidth = width - circleRadius * 2f
        val stepSize = calcWidth / max
        val stickTop = height * 0.5f - stickHeight * 0.5f


        for (i in progress until max) {
            val startPoint = i * stepSize + circleRadius
            val endPoint = (i + 1) * stepSize + circleRadius
            canvas.drawRect(startPoint, stickTop, endPoint, stickTop + stickHeight, backgroundPaint)
            canvas.drawCircle(endPoint, height * 0.5f, circleRadius, backgroundPaint)
        }

        for (i in 0 until progress) {
            val startPoint = i * stepSize + circleRadius
            val endPoint = (i + 1) * stepSize + circleRadius
            canvas.drawRect(startPoint, stickTop, endPoint, stickTop + stickHeight, fillPaint)
            canvas.drawCircle(endPoint, height * 0.5f, circleRadius, fillPaint)
        }

        canvas.drawCircle(circleRadius, height * 0.5f, circleRadius, fillPaint)


//        val margin = max.toFloat() / SPLIT_LINE_WIDTH_RATE
//        for (i in 0..sections.size) {
//            val prevSection = if (i == 0) 0f else sections[i - 1].toFloat() + margin
//            val posSection =
//                if (i == sections.size) max.toFloat() else sections[i].toFloat() - margin
//
//            val startPoint = prevSection / max * width
//            val endPoint = posSection / max * width
//
//            if (posSection < progress) {
//                canvas.drawRect(startPoint, 0f, endPoint, height.toFloat(), fillPaint)
//            } else {
//                canvas.drawRect(startPoint, 0f, endPoint, height.toFloat(), backgroundPaint)
//                canvas.drawRect(
//                    startPoint,
//                    0f,
//                    max(0f, progress - prevSection) / max * width + startPoint,
//                    height.toFloat(),
//                    fillPaint
//                )
//            }
//        }
    }
}