package com.example.pullcode.ui.category.products.hotels

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.pullcode.R
import com.example.pullcode.databinding.ActivityHotelsBinding
import com.example.pullcode.response.product.Data
import com.example.pullcode.ui.category.products.AdapterHotelResto
import com.example.pullcode.ui.category.products.ProductsDetailActivity

class HotelsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHotelsBinding
    private var hotelList: ArrayList<Data> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHotelsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            finish()
        }
        hotelList = intent.getParcelableArrayListExtra<Data>("data")!!

        Glide.with(this)
            .load("https://firebasestorage.googleapis.com/v0/b/galvanic-fort-333512.appspot.com/o/1%20(3).jpg?alt=media&token=694ec555-c624-4623-978c-676ec0dfb30b")
            .placeholder(R.drawable.animation)
            .into(binding.ivSponsor)

        binding.rvHotelSearch.layoutManager = LinearLayoutManager(this)
        binding.rvHotel.layoutManager = LinearLayoutManager(this)
        var adapter = AdapterHotelResto(hotelList) {
            val intent = Intent(this, ProductsDetailActivity::class.java)
                .putExtra("data", it)
            startActivity(intent)
        }
        binding.rvHotel.adapter = adapter


        binding.searchHotel.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean = false

            override fun onQueryTextChange(p0: String?): Boolean {

                if(p0 != null) {
                    if(p0.isEmpty()) {
                        binding.rvHotel.visibility = View.VISIBLE
                        binding.rvHotelSearch.visibility = View.GONE
                    }else if(p0.length > 2) {
                        val filter = hotelList.filter { it.name?.contains("$p0", true) == true }
                        adapter = AdapterHotelResto(filter as ArrayList<Data>) {
                            val intent = Intent(this@HotelsActivity, ProductsDetailActivity::class.java)
                                .putExtra("data",it)
                            startActivity(intent)
                        }
                    }

                    if (p0.isNotEmpty()) {
                        binding.rvHotel.visibility = View.INVISIBLE
                        binding.rvHotelSearch.visibility = View.VISIBLE
                        binding.rvHotelSearch.adapter = adapter
                    }
                    else {
                        binding.rvHotel.visibility = View.VISIBLE
                        binding.rvHotelSearch.visibility = View.GONE
                    }
                }

                return false
            }

        })
    }
}