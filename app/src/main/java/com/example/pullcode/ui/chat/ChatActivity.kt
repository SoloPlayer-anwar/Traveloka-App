package com.example.pullcode.ui.chat

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.pullcode.R
import com.example.pullcode.databinding.ActivityChatBinding
import com.example.pullcode.response.Chat
import com.example.pullcode.response.transaction.Data
import com.example.pullcode.ui.dashboard.BottomNavigationActivity
import com.example.pullcode.utils.Helpers.formatPrice
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    var filePath: String? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnSend.setOnClickListener {
            saveKomentar()
            clearData()
        }

        binding.back.setOnClickListener {
            startActivity(Intent(this, BottomNavigationActivity::class.java))
        }

        val data = intent.getParcelableExtra<Data>("data")

        if (data == null) {
            binding.linearChat.visibility = View.INVISIBLE


        } else {
            binding.linearChat.visibility = View.VISIBLE
            binding.btnSend.setOnClickListener {
                binding.linearChat.visibility = View.INVISIBLE
                saveKomentar()
                clearData()
            }
            binding.linearChat.clearFocus()
            Glide.with(this)
                .load(data.destinasi?.picturePath)
                .placeholder(R.drawable.animation)
                .into(binding.ivProduct)

            binding.tvName.text = data.destinasi?.name
            binding.tvTotal.formatPrice(data.total.toString())
        }


    }

    private fun clearData() {
        binding.etPesan.text.clear()
    }

    private fun saveKomentar() {
        val mDatabaseReference = FirebaseDatabase.getInstance()
        val data = intent.getParcelableExtra<Data>("data")
        val nameUser = data?.user?.name
        val pesan = binding.etPesan.text
        val calendar = Calendar.getInstance()
        val time = DateFormat.getDateInstance(SimpleDateFormat.FULL).format(calendar.time)
        val waktu = time.toString()
        filePath = data?.destinasi?.picturePath
        val productImage = filePath
        val nameProduct = data?.destinasi?.name
        val priceProduct = data?.total

        if (pesan.isEmpty()) {
            binding.etPesan.error = "tulisan komentar anda bro"
        }

        val komentarUser = mDatabaseReference.getReference("Komentar").push().key

        if (komentarUser != null) {
            val chatData = Chat(nameUser, pesan.toString(), waktu,productImage,nameProduct,priceProduct.toString())
            mDatabaseReference.getReference("Komentar").child(komentarUser).setValue(chatData)
                .addOnCompleteListener {

                }
        }
    }


}



