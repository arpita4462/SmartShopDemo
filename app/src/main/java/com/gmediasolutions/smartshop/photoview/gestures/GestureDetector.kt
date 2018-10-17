
package com.gmediasolutions.smartshop.photoview.gestures

import android.view.MotionEvent

interface GestureDetector {

    val isScalingjvm: Boolean

    val isDraggingjvm: Boolean

    fun onTouchEvent(ev: MotionEvent): Boolean

    fun setOnGestureListener(listener: OnGestureListener)

}
