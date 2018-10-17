package com.gmediasolutions.smartshop.options

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebView
import com.gmediasolutions.smartshop.Alert.ProgressDialog
import com.gmediasolutions.smartshop.R

class TermsCondActivity : AppCompatActivity() {

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
            info!!.loadUrl("file:///android_asset/termsCondition.html")
        } catch (e: Exception) {
            e.printStackTrace()

        }

    }
    override fun onStart() {
        super.onStart()
        try {
            pDialog!!.dismiss()
            info!!.loadUrl("file:///android_asset/termsCondition.html")
        } catch (e: Exception) {
            e.printStackTrace()

        }
    }

}
