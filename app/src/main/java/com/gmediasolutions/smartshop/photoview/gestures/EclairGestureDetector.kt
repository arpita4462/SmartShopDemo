
package com.gmediasolutions.smartshop.photoview.gestures

import android.annotation.TargetApi
import android.content.Context
import android.view.MotionEvent
import com.gmediasolutions.smartshop.photoview.view.Compat


@TargetApi(5)
open class EclairGestureDetector(context: Context) : CupcakeGestureDetector(context) {
    private var mActivePointerId = INVALID_POINTER_ID
    private var mActivePointerIndex = 0

    override fun getActiveX(ev: MotionEvent): Float {
        try {
            return ev.getX(mActivePointerIndex)
        } catch (e: Exception) {
            return ev.x
        }

    }

     override fun getActiveY(ev: MotionEvent): Float {
        try {
            return ev.getY(mActivePointerIndex)
        } catch (e: Exception) {
            return ev.y
        }

    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        val action = ev.action
        when (action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> mActivePointerId = ev.getPointerId(0)
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> mActivePointerId = INVALID_POINTER_ID
            MotionEvent.ACTION_POINTER_UP -> {
                // Ignore deprecation, ACTION_POINTER_ID_MASK and
                // ACTION_POINTER_ID_SHIFT has same value and are deprecated
                // You can have either deprecation or lint target api warning
                val pointerIndex = Compat.getPointerIndex(ev.action)
                val pointerId = ev.getPointerId(pointerIndex)
                if (pointerId == mActivePointerId) {
                    // This was our active pointer going up. Choose a new
                    // active pointer and adjust accordingly.
                    val newPointerIndex = if (pointerIndex == 0) 1 else 0
                    mActivePointerId = ev.getPointerId(newPointerIndex)
                    mLastTouchX=ev.getX(newPointerIndex)
                    mLastTouchY=ev.getY(newPointerIndex)
                }
            }
        }

        mActivePointerIndex = ev
                .findPointerIndex(if (mActivePointerId != INVALID_POINTER_ID)
                    mActivePointerId
                else
                    0)
        return super.onTouchEvent(ev)
    }

    companion object {

        private val INVALID_POINTER_ID = -1
    }
}
