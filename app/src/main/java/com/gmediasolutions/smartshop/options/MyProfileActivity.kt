package com.gmediasolutions.smartshop.options

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.gmediasolutions.smartshop.Alert.NetworkStateReceiver
import com.gmediasolutions.smartshop.Alert.ProgressDialog
import com.gmediasolutions.smartshop.MainActivity
import com.gmediasolutions.smartshop.R
import com.gmediasolutions.smartshop.usersession.UserSession
import com.gmediasolutions.smartshop.utility.ImageUrlUtils
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView

import java.io.ByteArrayOutputStream
import java.util.HashMap

import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_update_data.*
import java.io.File

class MyProfileActivity : AppCompatActivity(), NetworkStateReceiver.NetworkStateReceiverListener {

    private var session: UserSession? = null
    var user_token: String? = null
    var user_id: String? = null

    private var networkStateReceiver: NetworkStateReceiver? = null

    private var pDialog: ProgressDialog? = null

    private var button: Button? = null
    private var edtname: EditText? = null
    private var edtemail: EditText? = null
    private var edtmobile: EditText? = null
    private var primage: CircleImageView? = null
    private var namebutton: TextView? = null
    private var changeprofilepic: ImageView? = null

    private var name: String? = null
    private var email: String? = null
    private var photo: String? = null
    private var mobile: String? = null
    private var newemail: String? = null
    private var check: String? = null

    private var profileImage: String? = null
    private var profileImageUri: Uri? = null
    private var base64imgprofile: String? = null
    private var mCropImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_data)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        toolbar.setTitle(title)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)


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

        //initialize all fields
        namebutton = findViewById(R.id.name_button)
        primage = findViewById(R.id.profilepic)
        edtname = findViewById(R.id.name)
        edtemail = findViewById(R.id.email)
        edtmobile = findViewById(R.id.number)
        changeprofilepic = findViewById(R.id.changeprofilepic)
        button = findViewById(R.id.update)

        edtname!!.addTextChangedListener(nameWatcher)
        edtemail!!.addTextChangedListener(emailWatcher)
        edtmobile!!.addTextChangedListener(numberWatcher)

        //if data is already present fetch it from backend and display in ui
        //getData()

        //onclick of upload icon user should be able to click and change profile pic
        changeprofilepic!!.setOnClickListener {
            CropImage.startPickImageActivity(this)
        }

        //Updating user details and after update send user to profile page
        button!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {

                if (validateName() && validateEmail() && validateNumber()) {
                    name = edtname!!.text.toString()
                    newemail = edtemail!!.text.toString()
                    mobile = edtmobile!!.text.toString()

                } else {
                    Toast.makeText(applicationContext, "Incorrect Details Entered", Toast.LENGTH_LONG).show()
                }
            }
        })
    }


    //TextWatcher for Name -----------------------------------------------------

    internal var nameWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            check = s.toString()
            if (check!!.length < 4 || check!!.length > 20) {
                edtname!!.error = "Name Must consist of 4 to 20 characters"
            }
        }
    }

    //TextWatcher for Email -----------------------------------------------------

    internal var emailWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int){}
        override fun afterTextChanged(s: Editable) {
            check = s.toString()
            if (check!!.length < 4 || check!!.length > 40) {
                edtemail!!.error = "Email Must consist of 4 to 20 characters"
            } else if (!check!!.matches("^[A-za-z0-9.@]+".toRegex())) {
                edtemail!!.error = "Only . and @ characters allowed"
            } else if (!check!!.contains("@") || !check!!.contains(".")) {
                edtemail!!.error = "Enter Valid Email"
            }
        }
    }

    //TextWatcher for Mobile -----------------------------------------------------

    internal var numberWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            //none
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            //none
        }

        override fun afterTextChanged(s: Editable) {

            check = s.toString()

            if (check!!.length > 10) {
                edtmobile!!.error = "Number cannot be grated than 10 digits"
            } else if (check!!.length < 10) {
                edtmobile!!.error = "Number should be 10 digits"
            }
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            profileImageUri = CropImage.getPickImageResultUri(applicationContext!!, data)
            mCropImageUri = profileImageUri
            startCropImageActivity(profileImageUri)
        }
        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (data != null) {
                val result: CropImage.ActivityResult = CropImage.getActivityResult(data)
                if (resultCode == RESULT_OK) {
                    val result_uri = result.uri
                    val myFile = File(result_uri.getPath())
                    primage!!.setImageURI(result_uri)

                    val selectedcropImage = getImageContentUri(applicationContext, myFile)

                    val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                    val cursor = applicationContext!!.getContentResolver().query(selectedcropImage, filePathColumn, null, null, null)
                    cursor.moveToFirst()

                    val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                    profileImage = cursor.getString(columnIndex)
                    cursor.close()
                    //
                    updateprofilePic()
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                        Toast.makeText(applicationContext, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show()
                }
            } else {

            }
        }


    }
    //get Image from Imagecrop Fragment
    private fun getImageContentUri(context: Context?, myFile: File): Uri? {
        val filePath = myFile.getAbsolutePath()
        val cursor = context!!.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                arrayOf<String>(MediaStore.Images.Media._ID),
                MediaStore.Images.Media.DATA + "=? ",
                arrayOf<String>(filePath), null)

        if (cursor != null && cursor.moveToFirst()) {
            val id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID))
            val baseUri = Uri.parse("content://media/external/images/media")
            return Uri.withAppendedPath(baseUri, "" + id)
        } else {
            if (myFile.exists()) {
                val values = ContentValues()
                values.put(MediaStore.Images.Media.DATA, filePath)
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            } else {
                return null
            }
        }
    }

    //start CropImageActivity from Manifest declration
    private fun startCropImageActivity(profileImageUri: Uri?) {
        CropImage.activity(profileImageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .setAutoZoomEnabled(true)
                .setAspectRatio(100, 100)
                .setFixAspectRatio(true)
                .setCropShape(CropImageView.CropShape.OVAL)
                .start( this)
    }
    //        convertBitmapToString then in base 64
    private fun updateprofilePic() {
        if (profileImage != null) {
            val bm = BitmapFactory.decodeFile(profileImage)
            val bao = ByteArrayOutputStream()
            bm.compress(Bitmap.CompressFormat.JPEG, 90, bao)
            val byte = bao.toByteArray()
            base64imgprofile = Base64.encodeToString(byte, Base64.NO_WRAP)

//            val saveprofilepic = ProfilePicRequest(currentUserID!!, base64imgprofile!!)
//            saveindatabasepropic(saveprofilepic)
        } else {
                Toast.makeText(applicationContext, "Upload Image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateNumber(): Boolean {

        check = edtmobile!!.text.toString()
        Log.e("inside number", check!!.length.toString() + " ")
        if (check!!.length > 10) {
            return false
        } else if (check!!.length < 10) {
            return false
        }
        return true
    }

    private fun validateEmail(): Boolean {

        check = edtemail!!.text.toString()

        if (check!!.length < 4 || check!!.length > 40) {
            return false
        } else if (!check!!.matches("^[A-za-z0-9.@]+".toRegex())) {
            return false
        } else if (!check!!.contains("@") || !check!!.contains(".")) {
            return false
        }

        return true
    }

    private fun validateName(): Boolean {

        check = edtname!!.text.toString()
        return !(check!!.length < 4 || check!!.length > 20)

    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun viewCart(view: View) {
//        NotificationCountSetClass.setAddToCart(this, item, session!!.getCartValue())
        startActivity(Intent(this, CartListActivity::class.java))
        finish()
    }

    fun viewNotification(view: View) {
//        startActivity(Intent(this@MyProfileActivity, NotificationCountSetClass::class.java))
//        finish()
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
