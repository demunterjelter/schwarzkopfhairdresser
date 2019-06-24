package com.realdolmen.eindwerk.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.realdolmen.eindwerk.R
import com.realdolmen.eindwerk.data.Product
import com.squareup.picasso.Picasso

class ProductListAdapter(private val list: ArrayList<Product>, private val context: Context)
    : RecyclerView.Adapter<ProductListAdapter.ViewHolder>(){
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(product: Product) {
            var productName: TextView = itemView.findViewById(R.id.lv_name) as TextView
            var amount: TextView = itemView.findViewById(R.id.lv_amount) as TextView
            var volume: TextView = itemView.findViewById(R.id.lv_volume) as TextView
            var imagesOfProduct: ImageView = itemView.findViewById(R.id.imageView) as ImageView
            productName.text = product.productName
            amount.text = product.amount.toString()
            volume.text = product.volume
            Picasso.get()
                .load(product.img)
                .resize(500, 500)
                .centerCrop()
                .into(imagesOfProduct)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ProductListAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.card_view, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return list.size
    }
    override fun onBindViewHolder(holder: ProductListAdapter.ViewHolder, position: Int) {
        holder.bindItem(list[position])
    }

}