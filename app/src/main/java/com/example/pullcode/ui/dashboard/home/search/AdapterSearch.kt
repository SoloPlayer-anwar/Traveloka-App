package com.example.pullcode.ui.dashboard.home.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pullcode.R
import com.example.pullcode.response.destinasi.Data
import kotlinx.android.synthetic.main.item_search_layout.view.*

class AdapterSearch(private val listData: List<Data>,
                    private var listener: (Data) -> Unit):RecyclerView.Adapter<AdapterSearch.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterSearch.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val layoutView = layoutInflater.inflate(R.layout.item_search_layout,parent,false)
        return ViewHolder(layoutView)
    }

    override fun onBindViewHolder(holder: AdapterSearch.ViewHolder, position: Int) {
        holder.bindItem(listData[position],listener)
    }

    override fun getItemCount(): Int = listData.size
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        fun bindItem(data: Data, listener: (Data) -> Unit) {
            itemView.apply {
                tvCategory.text = data.name
                tvPlace.text = data.place

                itemView.setOnClickListener {
                    listener(data)
                }
            }
        }

    }
}