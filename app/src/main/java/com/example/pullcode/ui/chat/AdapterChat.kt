package com.example.pullcode.ui.chat

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pullcode.response.Chat

class AdapterChat(private var dataChat: ArrayList<Chat>,
                  private var listener: (Chat)-> Unit):RecyclerView.Adapter<AdapterChat.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterChat.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: AdapterChat.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int = dataChat.size
    class ViewHolder(view:View):RecyclerView.ViewHolder(view) {

    }
}