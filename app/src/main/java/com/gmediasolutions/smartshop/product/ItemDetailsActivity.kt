package com.gmediasolutions.smartshop.product

import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.gmediasolutions.smartshop.Alert.NetworkStateReceiver
import com.gmediasolutions.smartshop.Alert.ProgressDialog
import com.gmediasolutions.smartshop.MainActivity
import com.gmediasolutions.smartshop.R
import com.gmediasolutions.smartshop.fragments.ImageListFragment
import com.gmediasolutions.smartshop.fragments.ViewPagerActivity
import com.gmediasolutions.smartshop.options.CartListActivity
import com.gmediasolutions.smartshop.usersession.UserSession
import com.gmediasolutions.smartshop.utility.ImageUrlUtils

import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.android.synthetic.main.activity_item_details.*

class ItemDetailsActivity : AppCompatActivity(), NetworkStateReceiver.NetworkStateReceiverListener {
    private var session: UserSession? = null
    var user_token: String? = null
    var user_id: String? = null

    private var networkStateReceiver: NetworkStateReceiver? = null

    private var pDialog: ProgressDialog? = null

    internal var imagePosition: Int = 0
    internal var stringImageUri: String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_details)
        val mImageView = findViewById(R.id.image1) as SimpleDraweeView
        val textViewAddToCart = findViewById(R.id.text_action_bottom1) as TextView
        val textViewBuyNow = findViewById(R.id.text_action_bottom2) as TextView

        //check Internet Connection
        networkStateReceiver = NetworkStateReceiver()
        networkStateReceiver!!.addListener(this)
        this.registerReceiver(networkStateReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

        //shared prefenced
        session = UserSession(applicationContext)
        val loginuser: HashMap<String, String> = session!!.userDetails
        user_id = loginuser.get(UserSession.USER_ID)
        user_token = loginuser.get(UserSession.USER_TOKEN)

        pDialog = ProgressDialog(this)

        //Getting image uri from previous screen
        if (intent != null) {
            stringImageUri = intent.getStringExtra(ImageListFragment.STRING_IMAGE_URI)
            imagePosition = intent.getIntExtra(ImageListFragment.STRING_IMAGE_URI, 0)
        }


        val uri = Uri.parse(stringImageUri)
        mImageView.setImageURI(uri)
        mImageView.setOnClickListener {
            val intent = Intent(this@ItemDetailsActivity, ViewPagerActivity::class.java)
            intent.putExtra("position", imagePosition)
            startActivity(intent)
        }


        layout_action1.setOnClickListener {
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            val shareBody = "Found amazing " + "PRODUCT_NAME" + "on Magic Prints App"
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
            startActivity(Intent.createChooser(sharingIntent, "Share via"))
        }

        //Set click action for wishlist
//        layout_action3.setOnClickListener {
//            ImageUrlUtils.addWishlistImageUri(stringImageUri!!)
//            wishlist_icon.setImageResource(R.drawable.ic_favorite_black_18dp)
//        }

        textViewAddToCart.setOnClickListener {
//            val imageUrlUtils = ImageUrlUtils()
            ImageUrlUtils.addCartListImageUri(stringImageUri!!)
            Log.i("addToCartList09",stringImageUri.toString())
            Toast.makeText(this@ItemDetailsActivity, "Item added to cart.", Toast.LENGTH_SHORT).show()
//            session!!.increaseCartValue()
            MainActivity.countAddToCart++
            Log.i("addToCart",MainActivity.countAddToCart.toString())
//            NotificationCountSetClass.setNotifyCount(MainActivity.countAddToCart)
        }

        textViewBuyNow.setOnClickListener {
//            val imageUrlUtils = ImageUrlUtils()
            ImageUrlUtils.addCartListImageUri(stringImageUri!!)
            MainActivity.countAddToCart++
//            session!!.increaseCartValue()
//            NotificationCountSetClass.setNotifyCount(MainActivity.countAddToCart)
            startActivity(Intent(this@ItemDetailsActivity, CartListActivity::class.java))
        }

    }

    /*Checking Internet Connection and Showing Message*/
    private fun showSnack(isConnected: String) {
        val message: String
        val color: Int
        if (isConnected.equals("true")) {

        } else {
            message = getString(R.string.sorry_nointernet)
            color = Color.RED
            val snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
            val sbView = snackbar.view
            val textView = sbView.findViewById<View>(android.support.design.R.id.snackbar_text) as TextView
            textView.setTextColor(color)
            snackbar.show()
        }
    }

    override fun networkAvailable() {
        showSnack("true")
    }

    override fun networkUnavailable() {
        showSnack("false")
    }

    override fun onDestroy() {
        super.onDestroy()
        networkStateReceiver!!.removeListener(this)
        this.unregisterReceiver(networkStateReceiver)
    }

}
