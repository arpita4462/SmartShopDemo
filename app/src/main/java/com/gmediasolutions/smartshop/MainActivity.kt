package com.gmediasolutions.smartshop


import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v4.content.res.ResourcesCompat
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.gmediasolutions.smartshop.Alert.NetworkStateReceiver
import com.gmediasolutions.smartshop.Alert.ProgressDialog
import com.gmediasolutions.smartshop.adapter.FragmentAdapter
import com.gmediasolutions.smartshop.fragments.ImageListFragment
import com.gmediasolutions.smartshop.fragments.LayoutMainFragment
import com.gmediasolutions.smartshop.miscellaneous.EmptyActivity
import com.gmediasolutions.smartshop.options.*
import com.gmediasolutions.smartshop.usersession.UserSession
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, NetworkStateReceiver.NetworkStateReceiverListener{


    private var session: UserSession? = null
    var user_token: String? = null
    var user_id: String? = null

    private var networkStateReceiver: NetworkStateReceiver? = null

    private var pDialog: ProgressDialog? = null

    var viewPager: ViewPager? = null
    var tabLayout: TabLayout? = null


    companion object {
        //show count on cart in toolbar
       var countAddToCart=0
        fun getCartCount(){
            print("No. of objects="+ countAddToCart)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val typeface = ResourcesCompat.getFont(this, R.font.blacklist)
        appname_tvmain.setTypeface(typeface)

        //check Internet Connection
        networkStateReceiver = NetworkStateReceiver()
        networkStateReceiver!!.addListener(this)
        this.registerReceiver(networkStateReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

        //shared prefenced
        session = UserSession(applicationContext)
        val loginuser: HashMap<String, String> = session!!.userDetails
        user_id = loginuser.get(UserSession.USER_ID)
        user_token = loginuser.get(UserSession.USER_TOKEN)

        //check login
        session!!.isLoggedIn

        pDialog = ProgressDialog(this)

        //setup toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        //set navigation drawer open or close
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.setDrawerListener(toggle)
        toggle.syncState()

        //set view of navigation drawer
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        viewPager = findViewById<ViewPager>(R.id.viewpager)
        //tab layout with view pager
        tabLayout = findViewById<TabLayout>(R.id.tabs)
        if (viewPager != null) {
            setupViewPager(viewPager!!)
            tabLayout!!.setupWithViewPager(viewPager)
        }

        //get cart count from api and store it in countAddToCart

        count_tv.text= countAddToCart.toString()
        if(countAddToCart==0){
            counterValuePanel.visibility=View.GONE
        }else{
            counterValuePanel.visibility=View.VISIBLE
        }
//        NotificationCountSetClass.setAddToCart(this@MainActivity, view_cart, countAddToCart)
        Log.i("count_value", countAddToCart.toString())
        view_cart.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@MainActivity, CartListActivity::class.java))
        })
        view_search.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@MainActivity, SearchResultActivity::class.java))
        })
        view_notify.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@MainActivity, EmptyActivity::class.java))
        })

    }

    private fun setupViewPager(viewPager: ViewPager) {

        val adapter = FragmentAdapter(supportFragmentManager)
        var fragment = ImageListFragment()
        var fragmenthome = LayoutMainFragment()
        var bundle = Bundle()

        bundle.putInt("type", 0)
//        fragment.arguments = bundle
        adapter.addFragment(fragmenthome, getString(R.string.item_0))
        fragmenthome = LayoutMainFragment()
//        bundle = Bundle()
        bundle.putInt("type", 1)
        fragment.arguments = bundle
        adapter.addFragment(fragment, getString(R.string.item_1))
        fragment = ImageListFragment()
        bundle = Bundle()
        bundle.putInt("type", 2)
        fragment.arguments = bundle
        adapter.addFragment(fragment, getString(R.string.item_2))
        fragment = ImageListFragment()
        bundle = Bundle()
        bundle.putInt("type", 3)
        fragment.arguments = bundle
        adapter.addFragment(fragment, getString(R.string.item_3))
        fragment = ImageListFragment()
        bundle = Bundle()
        bundle.putInt("type", 4)
        fragment.arguments = bundle
        adapter.addFragment(fragment, getString(R.string.item_4))
        fragment = ImageListFragment()
        bundle = Bundle()
        bundle.putInt("type", 5)
        fragment.arguments = bundle
        adapter.addFragment(fragment, getString(R.string.item_5))
        fragment = ImageListFragment()
        bundle = Bundle()
        bundle.putInt("type", 6)
        fragment.arguments = bundle
        adapter.addFragment(fragment, getString(R.string.item_6))
        viewPager.adapter = adapter
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.nav_item0) {
            viewPager!!.currentItem = 0
        } else if (id == R.id.nav_item1) {
            viewPager!!.currentItem = 1
        } else if (id == R.id.nav_item2) {
            viewPager!!.currentItem = 2
        } else if (id == R.id.nav_item3) {
            viewPager!!.currentItem = 3
        } else if (id == R.id.nav_item4) {
            viewPager!!.currentItem = 4
        } else if (id == R.id.nav_item5) {
            viewPager!!.currentItem = 5
        } else if (id == R.id.nav_item6) {
            viewPager!!.currentItem = 6
        } else if (id == R.id.my_orders) {
            startActivity(Intent(this@MainActivity, EmptyActivity::class.java))
        } else if (id == R.id.my_cart) {
            startActivity(Intent(this@MainActivity, CartListActivity::class.java))
        } else if (id == R.id.my_wishlist) {
            startActivity(Intent(this@MainActivity, MyWishListActivity::class.java))
        } else if (id == R.id.my_rewards) {
            startActivity(Intent(this@MainActivity, EmptyActivity::class.java))
        } else if (id == R.id.my_account) {
            startActivity(Intent(this@MainActivity, MyProfileActivity::class.java))
        } else if (id == R.id.terms_conditions) {
            startActivity(Intent(this@MainActivity, TermsCondActivity::class.java))
        } else if (id == R.id.privacy_policy) {
            startActivity(Intent(this@MainActivity, PrivacyPolicyActivity::class.java))
        } else if (id == R.id.contact_us) {
            startActivity(Intent(this@MainActivity, ContactUsActivity::class.java))
        } else if (id == R.id.log_out) {
            session!!.logoutUser()
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        } else {
            startActivity(Intent(this@MainActivity, EmptyActivity::class.java))
        }

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onResume() {
        super.onResume()
        count_tv.text= countAddToCart.toString()
        if(countAddToCart==0){
            counterValuePanel.visibility=View.GONE
        }else{
            counterValuePanel.visibility=View.VISIBLE
        }
    }


    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
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
