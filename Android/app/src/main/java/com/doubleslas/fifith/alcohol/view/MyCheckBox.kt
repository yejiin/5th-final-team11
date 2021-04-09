package com.doubleslas.fifith.alcohol.view

import android.content.Context
import android.util.AttributeSet

class MyCheckBox(context: Context, attrs: AttributeSet?, defStyle: Int) :
    androidx.appcompat.widget.AppCompatCheckBox(context, attrs, defStyle) {
    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    init {
        MyTextView.setDefaultFont(this, attrs)
    }

}