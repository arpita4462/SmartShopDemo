package com.gmediasolutions.smartshop.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.gmediasolutions.smartshop.R
import com.gmediasolutions.smartshop.fragments.ImageListFragment
import com.gmediasolutions.smartshop.product.ItemDetailsActivity
import com.gmediasolutions.smartshop.utility.ImageUrlUtils
import com.facebook.drawee.view.SimpleDraweeView

class WishListAdapter(val dataWishList: List<String>, val rowLayout: Int, val context: Context) : RecyclerView.Adapter<WishListAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishListAdapter.ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(rowLayout, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(dataWishList[position], position)
    }

    override fun getItemCount(): Int {
        return dataWishList.size
    }

    class ProductViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val mImageView: SimpleDraweeView
        val mLayoutItem: LinearLayout
        val mImageViewWishlist: ImageView

        init {
            mImageView = v.findViewById(R.id.image_wishlist) as SimpleDraweeView
            mLayoutItem = v.findViewById(R.id.layout_item_desc) as LinearLayout
            mImageViewWishlist = v.findViewById(R.id.ic_wishlist) as ImageView
        }

        fun bind(dataWishList: String, position: Int) {
            val uri = Uri.parse(dataWishList[position].toString())
//            itemView.tv_name.text = product.product_title
//            itemView.tv_price.text = "MinPrice : " + product.product_lowest_price


            mLayoutItem.setOnClickListener {
                val intent = Intent(itemView.context, ItemDetailsActivity::class.java)
                intent.putExtra(ImageListFragment.STRING_IMAGE_URI, dataWishList[position])
                intent.putExtra(ImageListFragment.STRING_IMAGE_POSITION, position)
                itemView.context.startActivity(intent)
            }

            mImageViewWishlist.setOnClickListener {
//                val imageUrlUtils = ImageUrlUtils()
                ImageUrlUtils.removeWishlistImageUri(position)
//                notifyDataSetChanged()
            }
        }

    }
}