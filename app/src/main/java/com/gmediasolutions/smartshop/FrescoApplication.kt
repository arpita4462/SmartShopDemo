package com.gmediasolutions.smartshop

import android.app.Application
import com.gmediasolutions.smartshop.cache.ImagePipelineConfigFactory
import com.facebook.drawee.backends.pipeline.Fresco

class FrescoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this, ImagePipelineConfigFactory.getImagePipelineConfig(this))
    }

}
