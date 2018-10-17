
package com.gmediasolutions.smartshop.photoview.scrollerproxy

import android.annotation.TargetApi
import android.content.Context

@TargetApi(14)
class IcsScroller(context: Context) : GingerScroller(context) {

    override fun computeScrollOffset(): Boolean {
        return mScroller.computeScrollOffset()
    }

}
