package com.gmediasolutions.smartshop.adapter

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import com.gmediasolutions.smartshop.photoview.view.PhotoView
import com.gmediasolutions.smartshop.utility.ImageUrlUtils

class SamplePagerAdapter : PagerAdapter() {

//     val imageUrlUtils = ImageUrlUtils()
     private val sDrawables = ImageUrlUtils.imageUrls

    override fun getCount(): Int {
        return sDrawables.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): View {
        val photoView = PhotoView(container.context)
        photoView.setImageUri(sDrawables[position])

        // Now just add PhotoView to ViewPager and return it
        container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        return photoView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

}
