package com.gmediasolutions.smartshop.options

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.gmediasolutions.smartshop.Alert.NetworkStateReceiver
import com.gmediasolutions.smartshop.Alert.ProgressDialog
import com.gmediasolutions.smartshop.MainActivity
import com.gmediasolutions.smartshop.R
import com.gmediasolutions.smartshop.fragments.ImageListFragment.Companion.STRING_IMAGE_POSITION
import com.gmediasolutions.smartshop.fragments.ImageListFragment.Companion.STRING_IMAGE_URI
import com.gmediasolutions.smartshop.product.ItemDetailsActivity
import com.gmediasolutions.smartshop.usersession.UserSession
import com.gmediasolutions.smartshop.utility.ImageUrlUtils
import com.facebook.drawee.view.SimpleDraweeView

import java.util.ArrayList
import java.util.HashMap

class CartListActivity : AppCompatActivity(), NetworkStateReceiver.NetworkStateReceiverListener  {

    private var session: UserSession? = null
    var user_token: String? = null
    var user_id: String? = null

    private var networkStateReceiver: NetworkStateReceiver? = null
    private var pDialog: ProgressDialog? = null
    private var mContext: Context? = null

    companion object {
        var mContext: Context? = null
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_list)
        mContext = this@CartListActivity

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

        val cartlistImageUri = ImageUrlUtils.getCartListImageUri()

        //Show cart layout based on items
        setCartLayout()

        val recyclerView = findViewById(R.id.recyclerview) as RecyclerView
        val recylerViewLayoutManager = LinearLayoutManager(mContext)

        recyclerView.layoutManager = recylerViewLayoutManager
        recyclerView.adapter = CartListActivity.SimpleStringRecyclerViewAdapter(recyclerView, cartlistImageUri)
    }

    class SimpleStringRecyclerViewAdapter(private val mRecyclerView: RecyclerView, private val mCartlistImageUri: ArrayList<String>) : RecyclerView.Adapter<CartListActivity.SimpleStringRecyclerViewAdapter.ViewHolder>() {

        class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
            val mImageView: SimpleDraweeView
            val mLayoutItem: LinearLayout
            val mLayoutRemove: LinearLayout
            val mLayoutEdit: LinearLayout

            init {
                mImageView = mView.findViewById(R.id.image_cartlist) as SimpleDraweeView
                mLayoutItem = mView.findViewById(R.id.layout_item_desc) as LinearLayout
                mLayoutRemove = mView.findViewById(R.id.layout_action1) as LinearLayout
                mLayoutEdit = mView.findViewById(R.id.layout_action2) as LinearLayout
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartListActivity.SimpleStringRecyclerViewAdapter.ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_cartlist_item, parent, false)
            return CartListActivity.SimpleStringRecyclerViewAdapter.ViewHolder(view)
        }

        override fun onViewRecycled(holder: CartListActivity.SimpleStringRecyclerViewAdapter.ViewHolder) {
            if (holder.mImageView.controller != null) {
                holder.mImageView.controller!!.onDetach()
            }
            if (holder.mImageView.topLevelDrawable != null) {
                holder.mImageView.topLevelDrawable!!.callback = null
                //                ((BitmapDrawable) holder.mImageView.getTopLevelDrawable()).getBitmap().recycle();
            }
        }

        override fun onBindViewHolder(holder: CartListActivity.SimpleStringRecyclerViewAdapter.ViewHolder, position: Int) {
            val uri = Uri.parse(mCartlistImageUri[position])
            holder.mImageView.setImageURI(uri)
            holder.mLayoutItem.setOnClickListener {
                val intent = Intent(mContext, ItemDetailsActivity::class.java)
                intent.putExtra(STRING_IMAGE_URI, mCartlistImageUri[position])
                intent.putExtra(STRING_IMAGE_POSITION, position)
                mContext!!.startActivity(intent)
            }

            //Set click action
            holder.mLayoutRemove.setOnClickListener {
                ImageUrlUtils.removeCartListImageUri(position)
                notifyDataSetChanged()
                //Decrease notification count

                MainActivity.countAddToCart--
            }

            //Set click action
            holder.mLayoutEdit.setOnClickListener { }
        }

        override fun getItemCount(): Int {
            return mCartlistImageUri.size
        }
    }

    protected fun setCartLayout() {
        val layoutCartItems = findViewById(R.id.layout_items) as LinearLayout
        val layoutCartPayments = findViewById(R.id.layout_payment) as LinearLayout
        val layoutCartNoItems = findViewById(R.id.layout_cart_empty) as LinearLayout
        Log.i("addToCartList",MainActivity.countAddToCart.toString())

        if (MainActivity.countAddToCart > 0) {
            layoutCartNoItems.visibility = View.GONE
            layoutCartItems.visibility = View.VISIBLE
            layoutCartPayments.visibility = View.VISIBLE
        } else {
            layoutCartNoItems.visibility = View.VISIBLE
            layoutCartItems.visibility = View.GONE
            layoutCartPayments.visibility = View.GONE

            val bStartShopping = findViewById(R.id.bAddNew) as Button
            bStartShopping.setOnClickListener { finish() }
        }
    }



    override fun onResume() {
        super.onResume()
        networkStateReceiver = NetworkStateReceiver()
        networkStateReceiver!!.addListener(this)
        this.registerReceiver(networkStateReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onStop() {
        super.onStop()

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
