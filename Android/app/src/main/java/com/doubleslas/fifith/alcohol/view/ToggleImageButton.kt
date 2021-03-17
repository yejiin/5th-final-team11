package com.doubleslas.fifith.alcohol.view

import android.content.Context
import android.util.AttributeSet
import android.widget.Checkable
import com.doubleslas.fifith.alcohol.R


class ToggleImageButton(context: Context, attrs: AttributeSet?, defStyle: Int) :
    androidx.appcompat.widget.AppCompatImageButton(context, attrs, defStyle)
    , Checkable {
    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    init {
        if (attrs != null) {
            setChecked(attrs)
        }
    }

    override fun performClick(): Boolean {
        toggle()
        return super.performClick()
    }

    override fun isChecked(): Boolean {
        return isSelected
    }

    override fun toggle() {
        isChecked = !isChecked
    }

    override fun setChecked(checked: Boolean) {
        isSelected = checked
    }

    private fun setChecked(attrs: AttributeSet) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.ToggleImageButton)
        isChecked = a.getBoolean(R.styleable.ToggleImageButton_android_checked, false)
        a.recycle()
    }

}