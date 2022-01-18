package com.example.pullcode.ui.category.holidays

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.pullcode.R
import com.example.pullcode.databinding.ActivityAdventureBinding
import com.example.pullcode.response.destinasi.Data
import com.example.pullcode.ui.category.DetailDestinasiActivity

class AdventureActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdventureBinding
    private var adventureList: ArrayList<Data> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdventureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            finish()
        }

        adventureList = intent.getParcelableArrayListExtra<Data>("data")!!

        Glide.with(this)
            .load("https://firebasestorage.googleapis.com/v0/b/galvanic-fort-333512.appspot.com/o/1%20(1).jpg?alt=media&token=bee71a27-6b81-4626-abf2-69ae3831bdd7")
            .placeholder(R.drawable.animation)
            .into(binding.ivSponsor)

        binding.rvAdventure.layoutManager = LinearLayoutManager(this)
        binding.rvAdventureSearch.layoutManager = LinearLayoutManager(this)
        var adapter = AdapterHolidays(adventureList) {
            val intent = Intent(this, DetailDestinasiActivity::class.java)
                .putExtra("des", it)
            startActivity(intent)
        }
        binding.rvAdventure.adapter = adapter

        binding.searchAdventure.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean = false

            override fun onQueryTextChange(p0: String?): Boolean {

                if(p0 != null) {
                    if (p0.isEmpty()){
                        binding.rvAdventure.visibility = View.VISIBLE
                        binding.rvAdventureSearch.visibility = View.GONE
                    }else if(p0.length > 2) {
                        val filter = adventureList.filter { it.name?.contains("$p0", true) == true }
                        adapter = AdapterHolidays(filter as ArrayList<Data>) {
                            val intent = Intent(this@AdventureActivity, DetailDestinasiActivity::class.java)
                                .putExtra("des", it)
                            startActivity(intent)
                        }
                    }
                    if (p0.isNotEmpty()) {
                        binding.rvAdventure.visibility = View.INVISIBLE
                        binding.rvAdventureSearch.visibility = View.VISIBLE
                        binding.rvAdventureSearch.adapter = adapter
                    }else {
                        binding.rvAdventure.visibility = View.VISIBLE
                        binding.rvAdventureSearch.visibility = View.GONE
                    }
                }

                return false
            }

        })
    }
}