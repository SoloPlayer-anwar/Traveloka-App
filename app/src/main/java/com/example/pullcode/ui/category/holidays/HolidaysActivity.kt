package com.example.pullcode.ui.category.holidays

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.pullcode.R
import com.example.pullcode.databinding.ActivityHolidaysBinding
import com.example.pullcode.response.destinasi.Data
import com.example.pullcode.ui.category.DetailDestinasiActivity

class HolidaysActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHolidaysBinding
    private var destinationList: ArrayList<Data> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHolidaysBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            finish()
        }

        destinationList = intent.getParcelableArrayListExtra<Data>("data")!!

        Glide.with(this)
            .load("https://firebasestorage.googleapis.com/v0/b/galvanic-fort-333512.appspot.com/o/1%20(2).jpg?alt=media&token=337738ef-7d82-4671-94ca-a9fa4d030a14")
            .placeholder(R.drawable.animation)
            .into(binding.ivSponsor)

        binding.rvDestination.layoutManager = LinearLayoutManager(this)
        binding.rvDestinationSearch.layoutManager = LinearLayoutManager(this)
        var adapter = AdapterHolidays(destinationList) {
            val intent = Intent(this, DetailDestinasiActivity::class.java)
                .putExtra("des", it)
            startActivity(intent)
        }
        binding.rvDestination.adapter = adapter


        binding.searchDestination.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean = false

            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0 != null) {
                    if (p0.isEmpty()) {
                        binding.rvDestination.visibility = View.VISIBLE
                        binding.rvDestinationSearch.visibility = View.GONE
                    }else if (p0.length > 2) {
                        val filter = destinationList.filter { it.name?.contains("$p0", true) == true }
                        adapter = AdapterHolidays(filter as ArrayList<Data>) {
                            val intent = Intent(this@HolidaysActivity, DetailDestinasiActivity::class.java)
                                .putExtra("des", it)
                            startActivity(intent)
                        }
                    }
                    if (p0.isNotEmpty()) {
                        binding.rvDestination.visibility = View.INVISIBLE
                        binding.rvDestinationSearch.visibility = View.VISIBLE
                        binding.rvDestinationSearch.adapter = adapter
                    }else {
                        binding.rvDestination.visibility = View.VISIBLE
                        binding.rvDestinationSearch.visibility = View.GONE
                    }
                }
                return false
            }

        })
    }
}