package com.gmediasolutions.smartshop.photoview.view
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.RectF
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.annotation.Nullable
import android.util.AttributeSet
import android.view.GestureDetector
import android.widget.ImageView
import com.gmediasolutions.smartshop.miscellaneous.CustomProgressbarDrawable
import com.gmediasolutions.smartshop.miscellaneous.ImageDownloadListener
import com.facebook.common.references.CloseableReference
import com.facebook.datasource.DataSource
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.controller.BaseControllerListener
import com.facebook.drawee.generic.GenericDraweeHierarchy
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder
import com.facebook.drawee.interfaces.DraweeController
import com.facebook.drawee.view.DraweeHolder
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.core.ImagePipeline
import com.facebook.imagepipeline.image.CloseableImage
import com.facebook.imagepipeline.image.CloseableStaticBitmap
import com.facebook.imagepipeline.image.ImageInfo
import com.facebook.imagepipeline.request.ImageRequest
import com.facebook.imagepipeline.request.ImageRequestBuilder
class PhotoView(context:Context, attr:AttributeSet, defStyle:Int):ImageView(context, attr, defStyle), IPhotoView, ImageDownloadListener {
    private var mAttacher:PhotoViewAttacher?=null
    private var mPendingScaleType:ScaleType?=null
    internal var mDraweeHolder:DraweeHolder<GenericDraweeHierarchy>?=null
    private var imageReference:CloseableReference<CloseableImage>? = null
    private var mDownloadListener:ImageDownloadListener ?= null
    override val displayRect:RectF
        get() {
            return mAttacher!!.displayRect
        }
    override val displayMatrix:Matrix
        get() {
            return mAttacher!!.displayMatrix
        }
   override var minScale:Float
        @Deprecated("")
        get() {
            return minimumScale
        }
        @Deprecated("")
        set(minScale) {
            minimumScale = minScale
        }
    override var minimumScale:Float
        get() {
            return mAttacher!!.minimumScale
        }
        set(minimumScale) {
            mAttacher!!.minimumScale=(minimumScale)
        }
    override var midScale:Float
        @Deprecated("")
        get() {
            return mediumScale
        }
        @Deprecated("")
        set(midScale) {
            mediumScale = midScale
        }
    override var mediumScale:Float
        get() {
            return mAttacher!!.mediumScale
        }
        set(mediumScale) {
            mAttacher!!.mediumScale=(mediumScale)
        }
    override var maxScale:Float
        @Deprecated("")
        get() {
            return maximumScale
        }
        @Deprecated("")
        set(maxScale) {
            maximumScale = maxScale
        }
    override var maximumScale:Float
        get() {
            return mAttacher!!.maximumScale
        }
        set(maximumScale) {
            mAttacher!!.maximumScale=(maximumScale)
        }
    override var scale:Float
        get() {
            return mAttacher!!.scale
        }
        set(scale) {
            mAttacher!!.scale=(scale)
        }

     override var scaleTypejvm:ScaleType
        get(){
            return mAttacher!!.scaleTypejvm
        }

        set(scaleTypejvm) = if (null != mAttacher)
        {
            mAttacher!!.scaleTypejvm=(scaleTypejvm)
        }
        else
        {
            mPendingScaleType = scaleTypejvm
        }
    override var onPhotoTapListener:PhotoViewAttacher.OnPhotoTapListener?
        get() {
            return mAttacher!!.onPhotoTapListener
        }
        set(listener) {
            mAttacher!!.onPhotoTapListener=(listener)
        }
   override var onViewTapListener:PhotoViewAttacher.OnViewTapListener?
        get() {
            return mAttacher!!.onViewTapListener
        }
        set(listener) {
            mAttacher!!.onViewTapListener=(listener)
        }
   override val visibleRectangleBitmap:Bitmap
        get() {
            return mAttacher!!.visibleRectangleBitmap
        }
   override val iPhotoViewImplementation:IPhotoView
        get() {
            return mAttacher!!
        }
    constructor(context:Context) : this(context, null!!)
    constructor(context:Context, attr:AttributeSet) : this(context, attr, 0) {
        init()
    }
    init{
        super.setScaleType(ScaleType.MATRIX)
        init()
    }
    protected fun init() {
        if (null == mAttacher || null == mAttacher!!.imageView)
        {
            mAttacher = PhotoViewAttacher(this)
        }
        if (null != mPendingScaleType)
        {
            scaleType = mPendingScaleType!!
            mPendingScaleType = null
        }
        if (mDraweeHolder == null)
        {
            val hierarchy = GenericDraweeHierarchyBuilder(getResources())
                    .setFadeDuration(300)
                    .setProgressBarImage(CustomProgressbarDrawable(this))
                    .build()
            mDraweeHolder = DraweeHolder.create(hierarchy, getContext())
        }
    }
    fun setImageDownloadListener(mDownloadListener:ImageDownloadListener) {
        this.mDownloadListener = mDownloadListener
    }
    /**
     * @deprecated use {@link #setRotationTo(float)}
     */
    @Deprecated("use {@link #setRotationTo(float)}")
    override fun setPhotoViewRotation(rotationDegree:Float) {
        mAttacher!!.setRotationTo(rotationDegree)
    }
    override fun setRotationTo(rotationDegree:Float) {
        mAttacher!!.setRotationTo(rotationDegree)
    }
    override fun setRotationBy(rotationDegree:Float) {
        mAttacher!!.setRotationBy(rotationDegree)
    }
    override fun canZoom():Boolean {
        return mAttacher!!.canZoom()
    }
    override fun setDisplayMatrix(finalRectangle:Matrix):Boolean {
        return mAttacher!!.setDisplayMatrix(finalRectangle)
    }
   override fun setAllowParentInterceptOnEdge(allow:Boolean) {
        mAttacher!!.setAllowParentInterceptOnEdge(allow)
    }
   override fun setScaleLevels(minimumScale:Float, mediumScale:Float, maximumScale:Float) {
        mAttacher!!.setScaleLevels(minimumScale, mediumScale, maximumScale)
    }
    // setImageBitmap calls through to this method
    override fun setImageDrawable(drawable:Drawable) {
        super.setImageDrawable(drawable)
        if (null != mAttacher)
        {
            mAttacher!!.update()
        }
    }
    override fun setImageResource(resId:Int) {
        super.setImageResource(resId)
        if (null != mAttacher)
        {
            mAttacher!!.update()
        }
    }
    override fun setImageURI(uri:Uri) {
        super.setImageURI(uri)
        if (null != mAttacher)
        {
            mAttacher!!.update()
        }
    }
    override fun setOnMatrixChangeListener(listener:PhotoViewAttacher.OnMatrixChangedListener) {
        mAttacher!!.setOnMatrixChangeListener(listener)
    }
    override fun setOnLongClickListener(l:OnLongClickListener) {
        mAttacher!!.setOnLongClickListener(l)
    }
    override fun setScale(scale:Float, animate:Boolean) {
        mAttacher!!.setScale(scale, animate)
    }
    override fun setScale(scale:Float, focalX:Float, focalY:Float, animate:Boolean) {
        mAttacher!!.setScale(scale, focalX, focalY, animate)
    }
   override fun setZoomable(zoomable:Boolean) {
        mAttacher!!.setZoomable(zoomable)
    }
   override fun setZoomTransitionDuration(milliseconds:Int) {
        mAttacher!!.setZoomTransitionDuration(milliseconds)
    }
    override fun setOnDoubleTapListener(newOnDoubleTapListener:GestureDetector.OnDoubleTapListener) {
        mAttacher!!.setOnDoubleTapListener(newOnDoubleTapListener)
    }
    override fun setOnScaleChangeListener(onScaleChangeListener:PhotoViewAttacher.OnScaleChangeListener) {
        mAttacher!!.setOnScaleChangeListener(onScaleChangeListener)
    }
    override fun onDetachedFromWindow() {
        mAttacher!!.cleanup()
        mDraweeHolder!!.onDetach()
        super.onDetachedFromWindow()
    }
    override fun onAttachedToWindow() {
        init()
        mDraweeHolder!!.onAttach()
        super.onAttachedToWindow()
    }
    override fun verifyDrawable(dr:Drawable):Boolean {
        super.verifyDrawable(dr)
        if (dr === mDraweeHolder!!.getHierarchy().getTopLevelDrawable())
        {
            return true
        }
        return false
    }
    override fun onStartTemporaryDetach() {
        super.onStartTemporaryDetach()
        mDraweeHolder!!.onDetach()
    }
    override fun onFinishTemporaryDetach() {
        super.onFinishTemporaryDetach()
        mDraweeHolder!!.onAttach()
    }
    fun setImageUri(url:String) {
        val imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url)).build()
        val imagePipeline = Fresco.getImagePipeline()
        val dataSource = imagePipeline.fetchDecodedImage(imageRequest, this)
        val controller = Fresco.newDraweeControllerBuilder()
                .setOldController(mDraweeHolder!!.getController())
                .setImageRequest(imageRequest)
                .setControllerListener(object:BaseControllerListener<ImageInfo>() {
                    fun onFinalImageSet(s:String, @Nullable imageInfo:ImageInfo, @Nullable animatable:Animatable) {
                        try
                        {
                            imageReference = dataSource.getResult()
                            if (imageReference != null)
                            {
                                val image = imageReference!!.get()
                                // do something with the image
                                if (image != null && image is CloseableStaticBitmap)
                                {
                                    val closeableStaticBitmap = image as CloseableStaticBitmap
                                    val bitmap = closeableStaticBitmap.getUnderlyingBitmap()
                                    if (bitmap != null)
                                    {
                                        setImageBitmap(bitmap)
                                    }
                                }
                            }
                        }
                        finally
                        {
                            dataSource.close()
                            CloseableReference.closeSafely(imageReference)
                        }
                    }
                })
                .setTapToRetryEnabled(true)
                .build()
        mDraweeHolder!!.setController(controller)
    }
    fun setImageUri(uri:String, width:Int, height:Int) {
        val imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(uri))
                .setAutoRotateEnabled(true)
                .setResizeOptions(ResizeOptions(width, height))
                .build()
        val imagePipeline = Fresco.getImagePipeline()
        val dataSource = imagePipeline.fetchDecodedImage(imageRequest, this)
        val controller = Fresco.newDraweeControllerBuilder()
                .setOldController(mDraweeHolder!!.getController())
                .setImageRequest(imageRequest)
                .setControllerListener(object:BaseControllerListener<ImageInfo>() {
                    fun onFinalImageSet(s:String, @Nullable imageInfo:ImageInfo, @Nullable animatable:Animatable) {
                        try
                        {
                            imageReference = dataSource.getResult()
                            if (imageReference != null)
                            {
                                val image = imageReference!!.get()
                                if (image != null && image is CloseableStaticBitmap)
                                {
                                    val closeableStaticBitmap = image as CloseableStaticBitmap
                                    val bitmap = closeableStaticBitmap.getUnderlyingBitmap()
                                    if (bitmap != null)
                                    {
                                        setImageBitmap(bitmap)
                                    }
                                }
                            }
                        }
                        finally
                        {
                            dataSource.close()
                            CloseableReference.closeSafely(imageReference)
                        }
                    }
                })
                .setTapToRetryEnabled(true)
                .build()
        mDraweeHolder!!.setController(controller)
    }
    override fun onUpdate(progress:Int) {
        if (mDownloadListener != null)
        {
            mDownloadListener!!.onUpdate(progress)
        }
    }
}