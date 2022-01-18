package com.example.pullcode.ui.category.holidays

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pullcode.R
import com.example.pullcode.response.destinasi.Data
import com.example.pullcode.utils.Helpers.formatPrice
import kotlinx.android.synthetic.main.item_all_category.view.*

class AdapterHolidays(private var destinationList: List<Data>,
                      private var listener: (Data) -> Unit)
    :RecyclerView.Adapter<AdapterHolidays.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterHolidays.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val layoutView = layoutInflater.inflate(R.layout.item_all_category,parent,false)
        return ViewHolder(layoutView)
    }

    override fun onBindViewHolder(holder: AdapterHolidays.ViewHolder, position: Int) {
        holder.bindItem(destinationList[position], listener)
    }

    override fun getItemCount(): Int = destinationList.size
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        fun bindItem(data: Data, listener: (Data) -> Unit) {
            itemView.apply {
                Glide.with(context)
                    .load(data.picturePath)
                    .placeholder(R.drawable.animation)
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