package com.gmediasolutions.smartshop.options

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebView
import com.gmediasolutions.smartshop.Alert.NetworkStateReceiver
import com.gmediasolutions.smartshop.Alert.ProgressDialog
import com.gmediasolutions.smartshop.MainActivity
import com.gmediasolutions.smartshop.R
import com.gmediasolutions.smartshop.fragments.ImageListFragment.Companion.STRING_IMAGE_POSITION
import com.gmediasolutions.smartshop.fragments.ImageListFragment.Companion.STRING_IMAGE_URI
import com.gmediasolutions.smartshop.product.ItemDetailsActivity
import com.gmediasolutions.smartshop.usersession.UserSession
import com.gmediasolutions.smartshop.utility.ImageUrlUtils

class PrivacyPolicyActivity : AppCompatActivity() {

    private var pDialog: ProgressDialog? = null

    var info: WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_termscond)
        info = findViewById(R.id.about_us_web)
        pDialog = ProgressDialog(this)
        pDialog!!.show()
        try {
            pDialog!!.dismiss()
            info!!.loadUrl("file:///android_asset/privacypolicy.html")
        } catch (e: Exception) {
            e.printStackTrace()

        }

    }
    override fun onStart() {
        super.onStart()
        try {
            pDialog!!.dismiss()
            info!!.loadUrl("file:///android_asset/privacypolicy.html")
        } catch (e: Exception) {
            e.printStackTrace()

        }
    }

}
