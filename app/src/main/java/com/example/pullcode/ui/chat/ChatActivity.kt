package com.example.pullcode.ui.chat

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.pullcode.R
import com.example.pullcode.Traveling
import com.example.pullcode.databinding.ActivityChatBinding
import com.example.pullcode.response.Chat
import com.example.pullcode.response.sign.User
import com.example.pullcode.response.transaction.Data
import com.example.pullcode.ui.dashboard.BottomNavigationActivity
import com.example.pullcode.utils.Helpers.formatPrice
import com.google.firebase.database.*
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.item_chat.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    var filePath: String? =null
    private var chatData = ArrayList<Chat>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getChat()

        binding.rvChat.layoutManager = LinearLayoutManager(this)

        binding.btnSend.setOnClickListener {
            saveKomentar()
            clearData()
            saveChat()
            saveProduct()
        }

        binding.back.setOnClickListener {
            startActivity(Intent(this, BottomNavigationActivity::class.java))
        }

        val data = intent.getParcelableExtra<Data>("data")
        val des = intent.getParcelableExtra<com.example.pullcode.response.destinasi.Data>("des")
        val product = intent.getParcelableExtra<com.example.pullcode.response.product.Data>("product")

        if (data == null) {
            binding.linearChat.visibility = View.GONE
            binding.view5.visibility = View.GONE
            binding.etPesan.visibility = View.GONE
            binding.btnSend.visibility = View.GONE

            if (des == null) {
                binding.layoutDetail.visibility = View.INVISIBLE
                binding.view5.visibility = View.GONE
                binding.etPesan.visibility = View.GONE
                binding.btnSend.visibility = View.GONE

                if (product == null) {
                    binding.layoutDetail.visibility = View.INVISIBLE
                    binding.view5.visibility = View.GONE
                    binding.etPesan.visibility = View.GONE
                    binding.btnSend.visibility = View.GONE
                }else {
                    binding.layoutDetail.visibility = View.VISIBLE
                    binding.view5.visibility = View.VISIBLE
                    binding.etPesan.visibility = View.VISIBLE
                    binding.btnSend.visibility = View.VISIBLE
                    binding.btnSend.setOnClickListener {
                        binding.layoutDetail.visibility = View.INVISIBLE
                        saveProduct()
                        clearData()
                    }
                    binding.layoutDetail.clearFocus()
                    Glide.with(this)
                        .load(product.picturePath)
                        .placeholder(R.drawable.animation)
                        .into(binding.ivProductDetail)

                    binding.tvNameDetail.text = product.name
                    binding.tvPriceDetail.formatPrice(product.price.toString())
                }
            }else {
                binding.layoutDetail.visibility = View.VISIBLE
                binding.view5.visibility = View.VISIBLE
                binding.etPesan.visibility = View.VISIBLE
                binding.btnSend.visibility = View.VISIBLE
                binding.btnSend.setOnClickListener {
                    binding.layoutDetail.visibility = View.INVISIBLE
                    saveChat()
                    clearData()
                }
                binding.layoutDetail.clearFocus()
                Glide.with(this)
                    .load(des.picturePath)
                    .placeholder(R.drawable.animation)
                    .into(binding.ivProductDetail)

                binding.tvNameDetail.text = des.name
                binding.tvPriceDetail.formatPrice(des.price.toString())
            }
        }
        else {
            binding.linearChat.visibility = View.VISIBLE
            binding.view5.visibility = View.VISIBLE
            binding.etPesan.visibility = View.VISIBLE
            binding.btnSend.visibility = View.VISIBLE
            binding.btnSend.setOnClickListener {
                binding.linearChat.visibility = View.GONE
                saveKomentar()
                clearData()
            }
            binding.linearChat.clearFocus()
            Glide.with(this)
                .load(data.pictureProduct)
                .placeholder(R.drawable.animation)
                .into(binding.ivProduct)

            binding.tvName.text = data.name
            binding.tvTotal.formatPrice(data.total.toString())

        }

    }

    private fun saveProduct() {
        val mDatabaseReference = FirebaseDatabase.getInstance()
        val product = intent.getParcelableExtra<com.example.pullcode.response.product.Data>("product")
        val user = Traveling.getApp().getUser()
        val userResponse = Gson().fromJson(user, User::class.java)
        val nameUser = userResponse.name
        val pesan = binding.etPesan.text
        val calendar = Calendar.getInstance()
        val time = DateFormat.getDateInstance(SimpleDateFormat.FULL).format(calendar.time)
        val waktu = time.toString()
        filePath = product?.picturePath
        val productImage = filePath
        val nameProduct = product?.name
        val priceProduct = product?.price

        if (pesan.isEmpty()) {
            binding.etPesan.error = "tulisan komentar anda bro"
        }

        val komentarUser = mDatabaseReference.getReference("Komentar").push().key

        if (komentarUser != null) {
            val chatData = Chat(pesan.toString(),waktu, nameProduct,priceProduct, productImage, nameUser)
            mDatabaseReference.getReference("Komentar").child(komentarUser).setValue(chatData)
                .addOnCompleteListener {

                }
        }
    }


    private fun getChat() {
        val mDatabaseReference = FirebaseDatabase.getInstance().getReference("Komentar")
        mDatabaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                chatData.clear()
                for (item in snapshot.children) {
                    val data = item.getValue(Chat::class.java)
                    chatData.add(data!!)

                    val name = data.nameUser
                    if(data.nameUser == name) {
                        binding.rvChat.adapter = AdapterChat(chatData)
                    }else {

                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, error.message,Toast.LENGTH_SHORT).show()
            }

        })
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
        filePath = data?.pictureProduct
        val productImage = filePath
        val nameProduct = data?.name
        val priceProduct = data?.total

        if (pesan.isEmpty()) {
            binding.etPesan.error = "tulisan komentar anda bro"
        }

        val komentarUser = mDatabaseReference.getReference("Komentar").push().key

        if (komentarUser != null) {
            val chatData = Chat(pesan.toString(),waktu, nameProduct,priceProduct, productImage, nameUser)
            mDatabaseReference.getReference("Komentar").child(komentarUser).setValue(chatData)
                .addOnCompleteListener {

                }
        }
    }

    private fun saveChat() {
        val mDatabaseReference = FirebaseDatabase.getInstance().getReference("Komentar")
        val des = intent.getParcelableExtra<com.example.pullcode.response.destinasi.Data>("des")
        val user = Traveling.getApp().getUser()
        val userResponse = Gson().fromJson(user, User::class.java)
        filePath = des?.picturePath
        val productImage = filePath
        val nameProduct = des?.name
        val priceProduct = des?.price
        val pesan = binding.etPesan.text
        val nameUser = userResponse.name
        val calendar = Calendar.getInstance()
        val time = DateFormat.getDateInstance(SimpleDateFormat.FULL).format(calendar.time)
        val waktu = time.toString()

        if (pesan.isEmpty()) {
            binding.etPesan.error = "isi komentar anda"
        }

        val komentarUser = mDatabaseReference.push().key

        if (komentarUser != null) {
            val chatData = Chat(pesan.toString(),waktu, nameProduct, priceProduct, productImage, nameUser)
            mDatabaseReference.child(komentarUser).setValue(chatData)
                .addOnCompleteListener {

                }
        }
    }

}



