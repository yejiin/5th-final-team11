package com.doubleslas.fifith.alcohol.view

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet

class MyTextView(context: Context, attrs: AttributeSet?, defStyle: Int) :
    androidx.appcompat.widget.AppCompatTextView(context, attrs, defStyle) {
    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    init {
        if (attrs != null) {
            var hasFont = false
            for (i in 0 until attrs.attributeCount) {
                if (attrs.getAttributeName(i) == "fontFamily" || attrs.getAttributeName(i) == "typeface") {
                    hasFont = true
                    break
                }
            }

            if (!hasFont) {
                typeface = notoSans
                includeFontPadding = false
            }
        }
    }

    companion object {
        private val notoSans = Typeface.createFromFile("/system/fonts/NotoSansCJK-Regular.ttc")
    }

}