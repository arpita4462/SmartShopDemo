package com.gmediasolutions.smartshop.adapter

import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.facebook.drawee.view.SimpleDraweeView
import com.gmediasolutions.smartshop.R
import com.gmediasolutions.smartshop.fragments.ImageListFragment
import com.gmediasolutions.smartshop.product.ItemDetailsActivity
import com.gmediasolutions.smartshop.utility.ImageUrlUtils

class SimpleStringRecyclerViewAdapter(private val mRecyclerView: RecyclerView, private val mValues: Array<String>) : RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onViewRecycled(holder: ViewHolder) {
        if (holder.mImageView.getController() != null) {
            holder.mImageView.getController()!!.onDetach()
        }
        if (holder.mImageView.getTopLevelDrawable() != null) {
            holder.mImageView.getTopLevelDrawable()!!.setCallback(null)
            //                ((BitmapDrawable) holder.mImageView.getTopLevelDrawable()).getBitmap().recycle();
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        /* FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) holder.mImageView.getLayoutParams();
        if (mRecyclerView.getLayoutManager() instanceof GridLayoutManager) {
            layoutParams.height = 200;
        } else if (mRecyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            layoutParams.height = 600;
        } else {
            layoutParams.height = 800;
        }*/
        val uri = Uri.parse(mValues[position])
        holder.mImageView.setImageURI(uri)
        holder.mLayoutItem.setOnClickListener {
            Log.i("item_index",position.toString())
            val intent = Intent(ImageListFragment.mActivity, ItemDetailsActivity::class.java)
            intent.putExtra(ImageListFragment.STRING_IMAGE_URI, mValues[position])
            intent.putExtra(ImageListFragment.STRING_IMAGE_POSITION, position)
            ImageListFragment.mActivity!!.startActivity(intent)
        }

        //Set click action for wishlist
        holder.mImageViewWishlist.setOnClickListener {
            Log.i("item_indeximg",position.toString())
//            val imageUrlUtils = ImageUrlUtils()
            ImageUrlUtils.addWishlistImageUri(mValues[position])
            holder.mImageViewWishlist.setImageResource(R.drawable.ic_favorite_black_18dp)
            notifyDataSetChanged()
            Toast.makeText(ImageListFragment.mActivity, "Item added to wishlist.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mImageView: SimpleDraweeView
        val mLayoutItem: LinearLayout
        val mImageViewWishlist: ImageView

        init {
            mImageView = mView.findViewById(R.id.image1) as SimpleDraweeView
            mLayoutItem = mView.findViewById(R.id.layout_item) as LinearLayout
            mImageViewWishlist = mView.findViewById(R.id.ic_wishlist) as ImageView
        }
    }

}
