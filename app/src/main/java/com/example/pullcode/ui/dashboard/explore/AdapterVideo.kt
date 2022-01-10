package com.example.pullcode.ui.dashboard.explore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pullcode.R
import com.example.pullcode.response.explore.Data
import kotlinx.android.synthetic.main.item_video.view.*

class AdapterVideo(private val listVideo: List<Data>,
                   private val itemAdapterCallback: ItemAdapterCallback)
    :RecyclerView.Adapter<AdapterVideo.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterVideo.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val layoutView = layoutInflater.inflate(R.layout.item_video, parent,false)
        return  ViewHolder(layoutView)
    }

    override fun onBindViewHolder(holder: AdapterVideo.ViewHolder, position: Int) {
        holder.bindItem(listVideo[position], itemAdapterCallback)
    }

    override fun getItemCount(): Int = listVideo.size
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        fun bindItem(data: Data, itemAdapterCallback: ItemAdapterCallback) {
            itemView.apply {
                Glide.with(context)
                    .load(data.photoDestination)
                    .placeholder(R.drawable.animation)
                    .into(ivDestination)

                Judul.text = data.judulGuide
                nameGuide.text = data.nameGuide
                Address.text = data.addressDestination

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