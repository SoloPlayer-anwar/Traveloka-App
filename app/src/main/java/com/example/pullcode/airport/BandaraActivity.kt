package com.example.pullcode.airport

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pullcode.R
import com.example.pullcode.databinding.ActivityBandaraBinding
import com.example.pullcode.response.bandara.BandaraResponse
import com.example.pullcode.response.bandara.Data

class BandaraActivity : AppCompatActivity(), BandaraContract.View{
    private lateinit var binding: ActivityBandaraBinding
    lateinit var presenter: BandaraPresenter
    var loadingDialog: Dialog? = null
    private var dataList: ArrayList<Data> = ArrayList()
    private var adapter : AdapterAirPort? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBandaraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = BandaraPresenter(this)
        presenter.submitAirport()
        initLoading()



    }

    private fun initLoading() {
        loadingDialog = Dialog(this)
        val dialogView = layoutInflater.inflate(R.layout.loading_dialog, null)

        loadingDialog.let {
            it?.setContentView(dialogView)
            it?.setCancelable(false)
            it?.window?.setBackgroundDrawableResource(R.color.tsp)
        }
    }

    override fun airportSuccess(bandaraResponse: BandaraResponse) {
        dataList = bandaraResponse.data as ArrayList<Data>
        adapter = AdapterAirPort(dataList) {
            setResult(Activity.RESULT_OK, Intent().putExtra("data",it))
            finish()
        }
        binding.rvBandara.layoutManager = LinearLayoutManager(this)
        binding.rvBandara.adapter = adapter

        binding.rvBandaraValidate.layoutManager = LinearLayoutManager(this)


        binding.searchView.imeOptions = EditorInfo.IME_ACTION_SEARCH
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean = false

            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0 != null) {
                    if (p0.isEmpty()) {
                        binding.rvBandara.visibility = View.VISIBLE
                        binding.rvBandaraValidate.visibility = View.GONE
                    }else if(p0.length > 2) {
                        val filter = dataList.filter { it.provinsi?.contains("$p0", true) == true }
                        adapter = AdapterAirPort(filter as ArrayList<Data>) {
                            setResult(RESULT_OK, Intent().putExtra("data", it))
                            finish()
                        }

                       if (p0.isNotEmpty()) {
                           binding.rvBandaraValidate.visibility = View.VISIBLE
                           binding.rvBandaraValidate.adapter = adapter

                           binding.rvBandara.visibility = View.INVISIBLE
                       }else {
                           binding.rvBandara.visibility = View.VISIBLE
                           binding.rvBandaraValidate.visibility = View.GONE
                       }
                    }
                }
                return false
            }
        })

    }

    override fun airportFailure(message: String) {
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show()
        Log.d("airportFailure", message)
    }

    override fun showLoading(loading: Boolean) {
       when(loading) {
           true -> {
               loadingDialog!!.show()
           }

           false -> {
               loadingDialog!!.dismiss()
           }
       }
    }

}