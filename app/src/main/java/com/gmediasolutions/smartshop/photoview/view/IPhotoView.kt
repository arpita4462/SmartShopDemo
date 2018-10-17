package com.gmediasolutions.smartshop.photoview.view

import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.RectF
import android.view.GestureDetector
import android.view.View
import android.widget.ImageView
import com.gmediasolutions.smartshop.photoview.view.PhotoViewAttacher

interface IPhotoView {

    /**
     * Gets the Display Rectangle of the currently displayed Drawable. The Rectangle is relative to
     * this View and includes all scaling and translations.
     *
     * @return - RectF of Displayed Drawable
     */
    val displayRect: RectF

    /**
     * Gets the Display Matrix of the currently displayed Drawable. The Rectangle is considered
     * relative to this View and includes all scaling and translations.
     *
     * @return - true if rectangle was applied successfully
     */
    val displayMatrix: Matrix

    /**
     * Use [.getMinimumScale] instead, this will be removed in future release
     *
     * @return The current minimum scale level. What this value represents depends on the current
     * [ImageView.ScaleType].
     */
    /**
     * Use [.setMinimumScale] instead, this will be removed in future
     * release
     *
     * &nbsp;
     * Sets the minimum scale level. What this value represents depends on the current [ ].
     *
     * @param minScale minimum allowed scale
     */
    @get:Deprecated("")
    @set:Deprecated("")
    var minScale: Float

    /**
     * @return The current minimum scale level. What this value represents depends on the current
     * [ImageView.ScaleType].
     */
    /**
     * Sets the minimum scale level. What this value represents depends on the current [ ].
     *
     * @param minimumScale minimum allowed scale
     */
    var minimumScale: Float

    /**
     * Use [.getMediumScale] instead, this will be removed in future release
     *
     * @return The current middle scale level. What this value represents depends on the current
     * [ImageView.ScaleType].
     */
    /**
     * Use [.setMediumScale] instead, this will be removed in future
     * release
     *
     * &nbsp;
     * Sets the middle scale level. What this value represents depends on the current [ ].
     *
     * @param midScale medium scale preset
     */
    @get:Deprecated("")
    @set:Deprecated("")
    var midScale: Float

    /**
     * @return The current medium scale level. What this value represents depends on the current
     * [ImageView.ScaleType].
     */
    /*
     * Sets the medium scale level. What this value represents depends on the current {@link android.widget.ImageView.ScaleType}.
     *
     * @param mediumScale medium scale preset
     */
    var mediumScale: Float

    /**
     * Use [.getMaximumScale] instead, this will be removed in future release
     *
     * @return The current maximum scale level. What this value represents depends on the current
     * [ImageView.ScaleType].
     */
    /**
     * Use [.setMaximumScale] instead, this will be removed in future
     * release
     *
     * &nbsp;
     * Sets the maximum scale level. What this value represents depends on the current [ ].
     *
     * @param maxScale maximum allowed scale preset
     */
    @get:Deprecated("")
    @set:Deprecated("")
    var maxScale: Float

    /**
     * @return The current maximum scale level. What this value represents depends on the current
     * [ImageView.ScaleType].
     */
    /**
     * Sets the maximum scale level. What this value represents depends on the current [ ].
     *
     * @param maximumScale maximum allowed scale preset
     */
    var maximumScale: Float

    /**
     * Returns the current scale value
     *
     * @return float - current scale value
     */
    /**
     * Changes the current scale to the specified value.
     *
     * @param scale - Value to scale to
     */
    var scale: Float

    /**
     * Return the current scale type in use by the ImageView.
     *
     * @return current ImageView.ScaleType
     */
    /**
     * Controls how the image should be resized or moved to match the size of the ImageView. Any
     * scaling or panning will happen within the confines of this [ ].
     *
     * @param scaleType - The desired scaling mode.
     */
    var scaleTypejvm: ImageView.ScaleType

    /**
     * Returns a listener to be invoked when the Photo displayed by this View is tapped with a
     * single tap.
     *
     * @return PhotoViewAttacher.OnPhotoTapListener currently set, may be null
     */
    /**
     * Register a callback to be invoked when the Photo displayed by this View is tapped with a
     * single tap.
     *
     * @param listener - Listener to be registered.
     */
    var onPhotoTapListener: PhotoViewAttacher.OnPhotoTapListener?

    /**
     * Returns a callback listener to be invoked when the View is tapped with a single tap.
     *
     * @return PhotoViewAttacher.OnViewTapListener currently set, may be null
     */
    /**
     * Register a callback to be invoked when the View is tapped with a single tap.
     *
     * @param listener - Listener to be registered.
     */
    var onViewTapListener: PhotoViewAttacher.OnViewTapListener?

    /**
     * Extracts currently visible area to Bitmap object, if there is no image loaded yet or the
     * ImageView is already destroyed, returns `null`
     *
     * @return currently visible area as bitmap or null
     */
    val visibleRectangleBitmap: Bitmap

    /**
     * Will return instance of IPhotoView (eg. PhotoViewAttacher), can be used to provide better
     * integration
     *
     * @return IPhotoView implementation instance if available, null if not
     */
    val iPhotoViewImplementation: IPhotoView

    /**
     * Returns true if the PhotoView is set to allow zooming of Photos.
     *
     * @return true if the PhotoView allows zooming.
     */
    fun canZoom(): Boolean

    /**
     * Sets the Display Matrix of the currently displayed Drawable. The Rectangle is considered
     * relative to this View and includes all scaling and translations.
     *
     * @param finalMatrix target matrix to set PhotoView to
     * @return - true if rectangle was applied successfully
     */
    fun setDisplayMatrix(finalMatrix: Matrix): Boolean

    /**
     * Whether to allow the ImageView's parent to intercept the touch event when the photo is scroll
     * to it's horizontal edge.
     *
     * @param allow whether to allow intercepting by parent element or not
     */
    fun setAllowParentInterceptOnEdge(allow: Boolean)

    /**
     * Allows to set all three scale levels at once, so you don't run into problem with setting
     * medium/minimum scale before the maximum one
     *
     * @param minimumScale minimum allowed scale
     * @param mediumScale  medium allowed scale
     * @param maximumScale maximum allowed scale preset
     */
    fun setScaleLevels(minimumScale: Float, mediumScale: Float, maximumScale: Float)

    /**
     * Register a callback to be invoked when the Photo displayed by this view is long-pressed.
     *
     * @param listener - Listener to be registered.
     */
    fun setOnLongClickListener(listener: View.OnLongClickListener)

    /**
     * Register a callback to be invoked when the Matrix has changed for this View. An example would
     * be the user panning or scaling the Photo.
     *
     * @param listener - Listener to be registered.
     */
    fun setOnMatrixChangeListener(listener: PhotoViewAttacher.OnMatrixChangedListener)

    /**
     * Enables rotation via PhotoView internal functions.
     *
     * @param rotationDegree - Degree to rotate PhotoView to, should be in range 0 to 360
     */
    fun setRotationTo(rotationDegree: Float)

    /**
     * Enables rotation via PhotoView internal functions.
     *
     * @param rotationDegree - Degree to rotate PhotoView by, should be in range 0 to 360
     */
    fun setRotationBy(rotationDegree: Float)

    /**
     * Changes the current scale to the specified value.
     *
     * @param scale   - Value to scale to
     * @param animate - Whether to animate the scale
     */
    fun setScale(scale: Float, animate: Boolean)

    /**
     * Changes the current scale to the specified value, around the given focal point.
     *
     * @param scale   - Value to scale to
     * @param focalX  - X Focus Point
     * @param focalY  - Y Focus Point
     * @param animate - Whether to animate the scale
     */
    fun setScale(scale: Float, focalX: Float, focalY: Float, animate: Boolean)

    /**
     * Allows you to enable/disable the zoom functionality on the ImageView. When disable the
     * ImageView reverts to using the FIT_CENTER matrix.
     *
     * @param zoomable - Whether the zoom functionality is enabled.
     */
    fun setZoomable(zoomable: Boolean)

    /**
     * Enables rotation via PhotoView internal functions. Name is chosen so it won't collide with
     * View.setRotation(float) in API since 11
     *
     * @param rotationDegree - Degree to rotate PhotoView to, should be in range 0 to 360
     */
    @Deprecated("use {@link #setRotationTo(float)}")
    fun setPhotoViewRotation(rotationDegree: Float)

    /**
     * Allows to change zoom transition speed, default value is 200 (PhotoViewAttacher.DEFAULT_ZOOM_DURATION).
     * Will default to 200 if provided negative value
     *
     * @param milliseconds duration of zoom interpolation
     */
    fun setZoomTransitionDuration(milliseconds: Int)

    /**
     * Sets custom double tap listener, to intercept default given functions. To reset behavior to
     * default, you can just pass in "null" or public field of PhotoViewAttacher.defaultOnDoubleTapListener
     *
     * @param newOnDoubleTapListener custom OnDoubleTapListener to be set on ImageView
     */
    fun setOnDoubleTapListener(newOnDoubleTapListener: GestureDetector.OnDoubleTapListener)

    /**
     * Will report back about scale changes
     *
     * @param onScaleChangeListener OnScaleChangeListener instance
     */
    fun setOnScaleChangeListener(onScaleChangeListener: PhotoViewAttacher.OnScaleChangeListener)

    companion object {

        val DEFAULT_MAX_SCALE = 3.0f
        val DEFAULT_MID_SCALE = 1.75f
        val DEFAULT_MIN_SCALE = 1.0f
        val DEFAULT_ZOOM_DURATION = 200
    }
}
