package com.gmediasolutions.smartshop.cache

import android.content.Context

import java.io.File

class FileCache(context: Context) {

    var cacheDir: File? = null
        private set

    init {
        //Find the dir to save cached images
        if (android.os.Environment.getExternalStorageState() == android.os.Environment.MEDIA_MOUNTED) {
            cacheDir = File(android.os.Environment.getExternalStorageDirectory(), "fresco_cache")
        } else {
            cacheDir = context.cacheDir
        }
        if (!cacheDir!!.exists()) {
            cacheDir!!.mkdirs()
        }
    }

    fun getFile(url: String): File? {
        val filename = url.hashCode().toString()
        val f = File(cacheDir, filename)
        return if (f.exists()) {
            f
        } else null
    }

    fun clear() {
        val files = cacheDir!!.listFiles() ?: return
        for (f in files) {
            f.delete()
        }
    }
}

