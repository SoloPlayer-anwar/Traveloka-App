package com.example.pullcode.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pullcode.R
import com.example.pullcode.response.Chat
import com.example.pullcode.utils.Helpers.formatPrice
import kotlinx.android.synthetic.main.item_chat.view.*

class AdapterChat(private var dataChat: ArrayList<Chat>):RecyclerView.Adapter<AdapterChat.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterChat.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val layoutView = layoutInflater.inflate(R.layout.item_chat, parent,false)
        return  ViewHolder(layoutView)
    }

    override fun onBindViewHolder(holder: AdapterChat.ViewHolder, position: Int) {
        holder.bindItem(dataChat[position])
    }

    override fun getItemCount(): Int = dataChat.size
    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        fun bindItem(chat: Chat) {
            itemView.apply {

                Glide.with(context)
                    .load(chat.productImage)
                    .into(ivProduct)

                tvProduct.text = chat.nameProduct
                tvTotal.formatPrice(chat.priceProduct.toString())

                tvNameUser.text = chat.nameUser

                tvChat.text = chat.pesan
                tvDate.text = chat.waktu
            }
        }

    }
}