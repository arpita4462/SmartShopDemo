package com.gmediasolutions.smartshop.model

data class ApiReturn(
        val reasonCode:Int,
        val reasonText:String,
        val data:String)