package com.example.pullcode.ui.dashboard.swab

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pullcode.R
import com.example.pullcode.response.transaction.Data
import com.example.pullcode.utils.Helpers.formatPrice
import kotlinx.android.synthetic.main.adapter_success.view.*

class AdapterSuccess(private var listSuccess: List<Data>,
                     private val itemAdapterCallback: ItemAdapterCallback)
    :RecyclerView.Adapter<AdapterSuccess.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterSuccess.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val layoutView = layoutInflater.inflate(R.layout.adapter_success,parent,false)
        return ViewHolder(layoutView)
    }

    override fun onBindViewHolder(holder: AdapterSuccess.ViewHolder, position: Int) {
        holder.bindItem(listSuccess[position],itemAdapterCallback)
    }

    override fun getItemCount(): Int = listSuccess.size
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        fun bindItem(data: Data, itemAdapterCallback: ItemAdapterCallback) {
            itemView.apply {
                Glide.with(context)
                    .load(data.pictureProduct)
                    .placeholder(R.drawable.animation)
                    .into(ivProduct)

                tvName.text = data.name
                tvPrice.formatPrice(data.total.toString())
                tvStatus.text = data.status

                itemView.setOnClickListener {
                    itemAdapterCallback.onClick(it,data)
                }
            }
        }

    }

    interface ItemAdapterCallback {
        fun onClick(v:View, data: Data)
    }

}