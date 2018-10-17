package com.gmediasolutions.smartshop.miscellaneous

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.drawable.Drawable

class CustomProgressbarDrawable(private val mListener: ImageDownloadListener?) : Drawable() {

    override fun draw(canvas: Canvas) {

    }

    override fun setAlpha(alpha: Int) {

    }

    override fun setColorFilter(cf: ColorFilter?) {

    }

    override fun getOpacity(): Int {
        return 0
    }

    override fun onLevelChange(level: Int): Boolean {
        val progress = (level / 10000.0 * 100).toInt()
        mListener?.onUpdate(progress)
        return super.onLevelChange(level)
    }
}
