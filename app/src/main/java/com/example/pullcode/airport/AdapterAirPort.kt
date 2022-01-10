package com.example.pullcode.airport

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pullcode.R
import com.example.pullcode.response.bandara.Data
import kotlinx.android.synthetic.main.item_bandara.view.*

class AdapterAirPort(private val listAirPort: List<Data>,
                     private var listenerList: (Data)-> Unit)
    :RecyclerView.Adapter<AdapterAirPort.ViewHolder>()

{

    lateinit var context:Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterAirPort.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val layoutView = layoutInflater.inflate(R.layout.item_bandara, parent,false)
        return ViewHolder(layoutView)
    }

    override fun onBindViewHolder(holder: AdapterAirPort.ViewHolder, position: Int) {
        holder.bindItem(listAirPort[position], listenerList)
    }

    override fun getItemCount(): Int = listAirPort.size
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        fun bindItem(data: Data, listenerList: (Data) -> Unit) {
            itemView.apply {
                tvProvinsi.text = data.provinsi
                tvBandara.text = data.namaBandara
                tvJam.text = data.jamTerbang

                Glide.with(context)
                    .load(data.picturePesawat)
                    .into(ivPesawat)


                itemView.setOnClickListener {
                    listenerList(data)
                }
            }
        }

    }

}