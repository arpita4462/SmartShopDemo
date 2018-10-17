package com.gmediasolutions.smartshop

import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.gmediasolutions.smartshop.Alert.NetworkStateReceiver
import com.gmediasolutions.smartshop.Alert.ProgressDialog
import com.gmediasolutions.smartshop.model.ApiReturn
import com.gmediasolutions.smartshop.model.ForgetPassRequest
import kotlinx.android.synthetic.main.activity_forgot_password.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ForgotPassword : AppCompatActivity(), NetworkStateReceiver.NetworkStateReceiverListener {

    private var networkStateReceiver: NetworkStateReceiver? = null

    private var pDialog: ProgressDialog? = null

    private var forgotpassemail: EditText? = null
    private var userEmail: String? = null
    private var reqPassWord: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        //check Internet Connection
        networkStateReceiver = NetworkStateReceiver()
        networkStateReceiver!!.addListener(this)
        this.registerReceiver(networkStateReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

        pDialog = ProgressDialog(this)


        val typeface = ResourcesCompat.getFont(this, R.font.blacklist)
        appname_tv.typeface = typeface

        forgotpassemail = findViewById(R.id.forgotpassemail)
        reqPassWord = findViewById(R.id.sendotp)

        reqPassWord!!.setOnClickListener {

            userEmail = forgotpassemail!!.text.toString().trim { it <= ' ' }

            if (TextUtils.isEmpty(userEmail) || !isEmailValid(userEmail!!)) {
                forgotpassemail!!.requestFocus()
                forgotpassemail!!.error = getString(R.string.hint_email)
            }else{
                Toast.makeText(this@ForgotPassword, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show()
            }

            /*pDialog!!.show()
            val forgetpwd = ForgetPassRequest(userEmail!!)
            val requestBody = HashMap<String, ForgetPassRequest>()
            requestBody["data"] = forgetpwd


            val retrofituser = Retrofit.Builder().addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(getString(R.string.base_url))
                    .build()
            val apiServiceuser = retrofituser.create(ApiInterface::class.java)
            val postUser = apiServiceuser.forgotPassword(requestBody)

            postUser.enqueue(object : Callback<ApiReturn> {

                override fun onFailure(call: Call<ApiReturn>, t: Throwable) {
                    pDialog!!.dismiss()
                    Toast.makeText(this@ForgotPassword, "Error", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<ApiReturn>, response: Response<ApiReturn>) {
                    val loginresponse = response.body()
                    if (loginresponse != null) {
                        if (response.isSuccessful) {
                            pDialog!!.dismiss()
                            Toast.makeText(this@ForgotPassword, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show()

                        } else {
                            pDialog!!.dismiss()
                            Toast.makeText(this@ForgotPassword, "Failed to send reset email!. Try Again!!!!!", Toast.LENGTH_SHORT).show()


                        }
                    } else {
                        pDialog!!.dismiss()
                        Toast.makeText(this@ForgotPassword, "Failed to send reset email!. Try Again!!!!!", Toast.LENGTH_SHORT).show()


                    }
                }

            })*/

        }
    }


    internal fun isEmailValid(email: CharSequence): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    override fun onStop() {
        super.onStop()

    }

    override fun onResume() {
        super.onResume()
        networkStateReceiver = NetworkStateReceiver()
        networkStateReceiver!!.addListener(this)
        this.registerReceiver(networkStateReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
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
