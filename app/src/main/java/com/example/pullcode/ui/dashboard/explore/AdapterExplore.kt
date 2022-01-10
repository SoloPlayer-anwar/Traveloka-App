package com.example.pullcode.ui.dashboard.explore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pullcode.R
import com.example.pullcode.response.explore.Data
import kotlinx.android.synthetic.main.item_user_explore.view.*

class AdapterExplore(private val listExplore: List<Data>,
                     private val itemAdapterCallback: ItemAdapterCallback)
    :RecyclerView.Adapter<AdapterExplore.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterExplore.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val layoutView = layoutInflater.inflate(R.layout.item_user_explore, parent,false)
        return  ViewHolder(layoutView)
    }

    override fun onBindViewHolder(holder: AdapterExplore.ViewHolder, position: Int) {
        holder.bindItem(listExplore[position], itemAdapterCallback)
    }

    override fun getItemCount(): Int = listExplore.size
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        fun bindItem(data: Data, itemAdapterCallback: ItemAdapterCallback) {
            itemView.apply {
                Glide.with(context)
                    .load(data.photoGuide)
                    .placeholder(R.drawable.animation)
                    .circleCrop()
                    .into(ivUser)

                tvUser.text = data.nameGuide

                itemView.setOnClickListener {
                    itemAdapterCallback.onClick(it,data)
                    ivAktif.visibility = View.VISIBLE
                }
            }
        }

    }

    interface ItemAdapterCallback {
        fun onClick(v:View, data: Data)
    }
}