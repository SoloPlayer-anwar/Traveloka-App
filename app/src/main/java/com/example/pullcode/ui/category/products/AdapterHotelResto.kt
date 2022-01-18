package com.example.pullcode.ui.category.products

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pullcode.R
import com.example.pullcode.response.product.Data
import com.example.pullcode.utils.Helpers.formatPrice
import kotlinx.android.synthetic.main.item_all_category.view.*

class AdapterHotelResto(private val listItem: List<Data>,
                        private val listener: (Data) -> Unit)
    :RecyclerView.Adapter<AdapterHotelResto.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): AdapterHotelResto.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val layoutView = layoutInflater.inflate(R.layout.item_all_category, parent,false)
        return ViewHolder(layoutView)
    }

    override fun onBindViewHolder(holder: AdapterHotelResto.ViewHolder, position: Int) {
        holder.bindItem(listItem[position], listener)
    }

    override fun getItemCount(): Int = listItem.size
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        fun bindItem(data: Data, listener: (Data) -> Unit) {
            itemView.apply {
                Glide.with(context)
                    .load(data.picturePath)
                    .into(ivProduct)

                tvNameResto.text = data.name
                rateResto.rating = data.rate?.toFloat() ?: 0f
                priceResto.formatPrice(data.price.toString())

                itemView.setOnClickListener {
                    listener(data)
                }
            }
        }

    }

}