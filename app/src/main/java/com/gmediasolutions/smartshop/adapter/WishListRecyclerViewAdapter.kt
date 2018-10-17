package com.gmediasolutions.smartshop.adapter

import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.gmediasolutions.smartshop.R
import com.gmediasolutions.smartshop.utility.ImageUrlUtils
import com.facebook.drawee.view.SimpleDraweeView
import java.util.ArrayList

class WishListRecyclerViewAdapter(private val mRecyclerView: RecyclerView, private val mWishlistImageUri: ArrayList<String>) : RecyclerView.Adapter<WishListRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mImageView: SimpleDraweeView
        val mLayoutItem: LinearLayout
        val mImageViewWishlist: ImageView

        init {
            mImageView = mView.findViewById(R.id.image_wishlist) as SimpleDraweeView
            mLayoutItem = mView.findViewById(R.id.layout_item_desc) as LinearLayout
            mImageViewWishlist = mView.findViewById(R.id.ic_wishlist) as ImageView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishListRecyclerViewAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_wishlist_item, parent, false)
        return WishListRecyclerViewAdapter.ViewHolder(view)
    }

    override fun onViewRecycled(holder: ViewHolder) {
        if (holder.mImageView.controller != null) {
            holder.mImageView.controller!!.onDetach()
        }
        if (holder.mImageView.topLevelDrawable != null) {
            holder.mImageView.topLevelDrawable!!.callback = null
            //                ((BitmapDrawable) holder.mImageView.getTopLevelDrawable()).getBitmap().recycle();
        }
    }

    override fun onBindViewHolder(holder: WishListRecyclerViewAdapter.ViewHolder, position: Int) {
        val uri = Uri.parse(mWishlistImageUri[position])
        holder.mImageView.setImageURI(uri)
/*
        holder.mLayoutItem.setOnClickListener {
            val intent = Intent(holder, ItemDetailsActivity::class.java)
            intent.putExtra(ImageListFragment.STRING_IMAGE_URI, mWishlistImageUri[position])
            intent.putExtra(ImageListFragment.STRING_IMAGE_POSITION, position)
            mContext!!.startActivity(intent)
        }
*/

        //Set click action for wishlist
        holder.mImageViewWishlist.setOnClickListener {
//            val imageUrlUtils = ImageUrlUtils()
            ImageUrlUtils.removeWishlistImageUri(position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return mWishlistImageUri.size
    }
}
