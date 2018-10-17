
package com.gmediasolutions.smartshop.fragments

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import com.gmediasolutions.smartshop.R
import com.gmediasolutions.smartshop.adapter.SamplePagerAdapter

class ViewPagerActivity : Activity() {
    private var mViewPager: ViewPager? = null
    private var position: Int = 0
    private val ISLOCKED_ARG = "isLocked"

    private val isViewPagerActive: Boolean
        get() = mViewPager != null && mViewPager is HackyViewPager

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager)
        mViewPager = findViewById(R.id.view_pager) as HackyViewPager
        setContentView(mViewPager)

        mViewPager!!.adapter = SamplePagerAdapter()
        if (intent != null) {
            position = intent.getIntExtra("position", 0)
            mViewPager!!.currentItem = position
        }

        if (savedInstanceState != null) {
            val isLocked = savedInstanceState.getBoolean(ISLOCKED_ARG, false)
            (mViewPager as HackyViewPager).isLocked = isLocked
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (isViewPagerActive) {
            outState.putBoolean(ISLOCKED_ARG, (mViewPager as HackyViewPager).isLocked)
        }
        super.onSaveInstanceState(outState)
    }

}
