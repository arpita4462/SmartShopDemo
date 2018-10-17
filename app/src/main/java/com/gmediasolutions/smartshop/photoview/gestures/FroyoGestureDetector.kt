
package com.gmediasolutions.smartshop.photoview.gestures

import android.annotation.TargetApi
import android.content.Context
import android.view.MotionEvent
import android.view.ScaleGestureDetector

@TargetApi(8)
open class FroyoGestureDetector(context: Context) : EclairGestureDetector(context) {

     protected val mDetector: ScaleGestureDetector

    init {
        val mScaleListener = object : ScaleGestureDetector.OnScaleGestureListener {

            override fun onScale(detector: ScaleGestureDetector): Boolean {
                val scaleFactor = detector.scaleFactor

                if (java.lang.Float.isNaN(scaleFactor) || java.lang.Float.isInfinite(scaleFactor))
                    return false

                mListener!!.onScale(scaleFactor, detector.focusX, detector.focusY)
                return true
            }

            override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
                return true
            }

            override fun onScaleEnd(detector: ScaleGestureDetector) {
            }
        }
        mDetector = ScaleGestureDetector(context, mScaleListener)
    }

    override val isScalingjvm: Boolean
        get() = mDetector.isInProgress

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        mDetector.onTouchEvent(ev)
        return super.onTouchEvent(ev)
    }

}
