package com.example.pullcode.ui.dashboard.home.search

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pullcode.R
import com.example.pullcode.databinding.ActivitySearchBinding
import com.example.pullcode.response.destinasi.Data
import com.example.pullcode.response.destinasi.DestinasiResponse
import com.example.pullcode.response.product.ProductResponse
import com.example.pullcode.ui.category.DetailDestinasiActivity
import com.example.pullcode.ui.dashboard.home.HomeContract
import com.example.pullcode.ui.dashboard.home.HomePresenter

class SearchActivity : AppCompatActivity(),HomeContract.View {
    private lateinit var binding: ActivitySearchBinding
    private var loadingDialog: Dialog? = null
    private lateinit var presenter: HomePresenter
    private var dataDestinasi: ArrayList<Data> = ArrayList()
    private var productList: ArrayList<com.example.pullcode.response.product.Data> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLoading()
        presenter = HomePresenter(this)
        presenter.getDestinasi()
        presenter.getHome()
        binding.back.setOnClickListener {
            finish()
        }



    }

    private fun initLoading() {
        loadingDialog = Dialog(this)
        val loadingView = layoutInflater.inflate(R.layout.loading_dialog, null)

        loadingDialog.let {
            it?.setContentView(loadingView)
            it?.setCancelable(false)
            it?.window?.setBackgroundDrawableResource(R.color.tsp)
        }
    }

    override fun homeSuccess(productResponse: ProductResponse) {

        var adapter = AdapterAll(productList) {

        }
        binding.rvProduct.layoutManager = LinearLayoutManager(this)

        productList = productResponse.data as ArrayList<com.example.pullcode.response.product.Data>
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String?): Boolean  = false
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    if (newText.isEmpty()) {
                        binding.constraint.visibility = View.VISIBLE
                        binding.rvProduct.visibility = View.INVISIBLE
                    }else if(newText.length > 2) {
                        val filter = productList.filter { it.name?.contains("$newText", true) == true }
                        adapter = AdapterAll(filter as ArrayList<com.example.pullcode.response.product.Data>){

                        }
                    }

                    if (newText.isNotEmpty()) {
                        binding.constraint.visibility = View.INVISIBLE
                        binding.rvProduct.visibility = View.VISIBLE
                        binding.rvProduct.adapter = adapter
                    }else {
                        binding.constraint.visibility = View.VISIBLE
                        binding.rvProduct.visibility = View.INVISIBLE
                    }
                }

                return false
            }
        })
    }

    override fun destinationSuccess(destinasiResponse: DestinasiResponse) {
        for (a in destinasiResponse.data.indices) {
            val items: List<String> = destinasiResponse.data[a].category?.split(",") ?: ArrayList()

            for( x in items.indices) {
                when {
                    items[x].equals("Adventure", true) -> {
                        dataDestinasi.add(destinasiResponse.data[a])
                    }
                }
            }
        }

        binding.rvPopulers.layoutManager = LinearLayoutManager(this)
        binding.rvPopulers.adapter = AdapterSearch(dataDestinasi) {
            val intent = Intent(this, DetailDestinasiActivity::class.java)
                .putExtra("des", it)
            startActivity(intent)
        }
    }

    override fun homeFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun destinationFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading(loading: Boolean) {
        when(loading) {
            true -> {
                loadingDialog?.show()
                loading
            }
            false -> {
                loadingDialog?.dismiss()
                loading
            }
        }
    }

}