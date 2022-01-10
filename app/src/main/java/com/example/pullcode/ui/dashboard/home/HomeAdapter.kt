package com.example.pullcode.ui.dashboard.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pullcode.R
import com.example.pullcode.response.product.Data
import com.example.pullcode.utils.Helpers.formatPrice
import kotlinx.android.synthetic.main.item_horizontal.view.*

class HomeAdapter(private val listRecommended: List<Data>,
                  private val itemAdapterCallback: ItemAdapterCallback):RecyclerView.Adapter<HomeAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val layoutView = layoutInflater.inflate(R.layout.item_horizontal, parent, false)
        return  ViewHolder(layoutView)
    }

    override fun onBindViewHolder(holder: HomeAdapter.ViewHolder, position: Int) {
        holder.bindItem(listRecommended[position], itemAdapterCallback)
    }

    override fun getItemCount(): Int = listRecommended.size
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        fun bindItem(data: Data, itemAdapterCallback: ItemAdapterCallback) {
            itemView.apply {
                Glide.with(context)
                    .load(data.picturePath)
                    .centerCrop()
                    .placeholder(R.drawable.animation)
                    .into(ivProduct)

                tvPrice.formatPrice(data.price.toString())
                tvName.text = data.name
                ratingBar.rating = data.rate?.toFloat() ?: 0f
                itemView.setOnClickListener {
                    itemAdapterCallback.onCLick(it, data)
                }
            }
        }

    }

    interface  ItemAdapterCallback {
        fun onCLick(v:View, data: Data)
    }
}