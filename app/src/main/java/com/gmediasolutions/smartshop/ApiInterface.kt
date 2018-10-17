package com.gmediasolutions.smartshop

import com.gmediasolutions.smartshop.model.ApiReturn
import com.gmediasolutions.smartshop.model.ForgetPassRequest
import com.gmediasolutions.smartshop.model.LoginRequest
import com.gmediasolutions.smartshop.model.SignUpModel
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    /*main page api */
    @Headers("Content-Type: application/json")
    @POST("/api/1.0/registration")
    fun registration(@Body data: HashMap<String, SignUpModel>): Call<ApiReturn>

    @Headers("Content-Type: application/json")
    @POST("/api/1.0/login")
    fun login(@Body data: HashMap<String, LoginRequest>)//: Call<LoginReturn>

    @Headers("Content-Type: application/json")
    @POST("/api/1.0/forgotPassword")
    fun forgotPassword(@Body data: HashMap<String, ForgetPassRequest>): Call<ApiReturn>

}