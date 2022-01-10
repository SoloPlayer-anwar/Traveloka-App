package com.example.pullcode.ui.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pullcode.R
import com.example.pullcode.response.destinasi.Data
import com.example.pullcode.utils.Helpers.formatPrice
import kotlinx.android.synthetic.main.item_horizontal.view.*

class AdapterDestinasi(private val desList: List<Data>,
                       private val onClickAdapter: OnClickAdapter
):RecyclerView.Adapter<AdapterDestinasi.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val layoutView = layoutInflater.inflate(R.layout.item_horizontal, parent, false)
        return  ViewHolder(layoutView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(desList[position], onClickAdapter)
    }

    override fun getItemCount(): Int = desList.size
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        fun bindItem(data: Data, onClickAdapter: OnClickAdapter) {
            itemView.apply {
                Glide.with(context)
                    .load(data.picturePath)
                    .centerCrop()
                    .into(ivProduct)

                tvPrice.formatPrice(data.price.toString())
                tvName.text = data.name
                ratingBar.rating = data.rate?.toFloat() ?: 0f

                itemView.setOnClickListener {
                    onClickAdapter.onCLick(it, data)
                }
            }
        }

    }

    interface  OnClickAdapter {
        fun onCLick(v:View, data: Data)
    }
}