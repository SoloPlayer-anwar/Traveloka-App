package com.example.pullcode.ui.category.products.resto

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.pullcode.R
import com.example.pullcode.databinding.ActivityRestoBinding
import com.example.pullcode.response.destinasi.DestinasiResponse
import com.example.pullcode.response.product.Data
import com.example.pullcode.response.product.ProductResponse
import com.example.pullcode.ui.category.products.AdapterHotelResto
import com.example.pullcode.ui.category.products.ProductsDetailActivity
import com.example.pullcode.ui.dashboard.home.HomeContract
import com.example.pullcode.ui.dashboard.home.HomePresenter

class RestoActivity : AppCompatActivity(){
    private lateinit var binding: ActivityRestoBinding
    private var restoList: ArrayList<Data> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.back.setOnClickListener {
            finish()
        }
        restoList = intent.getParcelableArrayListExtra<Data>("data")!!

        binding.rvResto.layoutManager = LinearLayoutManager(this)
        binding.rvRestoSearch.layoutManager = LinearLayoutManager(this)
        var adapter = AdapterHotelResto(restoList) {
            val intent = Intent(this, ProductsDetailActivity::class.java)
                .putExtra("data", it)
            startActivity(intent)
        }
        binding.rvResto.adapter = adapter

        Glide.with(this)
            .load("https://firebasestorage.googleapis.com/v0/b/galvanic-fort-333512.appspot.com/o/resto.jpg?alt=media&token=4da4872d-3acc-4f67-afe4-df74083e84ad")
            .placeholder(R.drawable.animation)
            .centerCrop()
            .into(binding.ivSponsor)

        binding.searchResto.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean = false

            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0 != null) {
                    if(p0.isEmpty()) {
                        binding.rvResto.visibility = View.VISIBLE
                        binding.rvRestoSearch.visibility = View.INVISIBLE
                    }else if(p0.length > 2) {
                        val filter = restoList.filter { it.name?.contains("$p0", true) == true }
                        adapter = AdapterHotelResto(filter as ArrayList<Data>) {
                            val intent = Intent(this@RestoActivity, ProductsDetailActivity::class.java)
                                .putExtra("data", it)
                            startActivity(intent)
                        }
                    }

                    if (p0.isNotEmpty()) {
                        binding.rvResto.visibility = View.INVISIBLE
                        binding.rvRestoSearch.visibility = View.VISIBLE
                        binding.rvRestoSearch.adapter = adapter
                    }else {
                        binding.rvResto.visibility = View.VISIBLE
                        binding.rvRestoSearch.visibility = View.INVISIBLE
                    }
                }

             return false
            }
        })
    }

}