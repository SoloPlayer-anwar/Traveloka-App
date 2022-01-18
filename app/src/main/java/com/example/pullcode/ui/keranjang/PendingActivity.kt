package com.example.pullcode.ui.keranjang

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pullcode.R
import com.example.pullcode.Traveling
import com.example.pullcode.databinding.ActivityPendingBinding
import com.example.pullcode.response.transaction.Data
import com.example.pullcode.response.transaction.Destinasi
import com.example.pullcode.response.transaction.Product
import com.example.pullcode.response.transaction.TransactionResponse
import com.example.pullcode.ui.chat.ChatActivity

class PendingActivity : AppCompatActivity(), PendingContract.View, AdapterPending.ItemAdapterCallback {
    private lateinit var binding: ActivityPendingBinding
    private lateinit var presenter: PendingPresenter
    var loadingDialog: Dialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPendingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = PendingPresenter(this)
        presenter.submitPending("PENDING")
        initLoading()

        binding.swipe.setOnRefreshListener {
            presenter.submitPending("PENDING")
        }

        binding.back.setOnClickListener {
            finish()
        }
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

    override fun transactionPending(transactionResponse: TransactionResponse) {
        Log.d("token", "ini token"+Traveling.getApp().getToken())
        binding.rvPending.layoutManager = LinearLayoutManager(this)
        binding.rvPending.adapter = AdapterPending(transactionResponse.data, this)

    }

    override fun transactionFailure(message: String) {
       Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }

    override fun showLoading(loading: Boolean) {
        when(loading) {
            true -> {
                loadingDialog?.show()
                binding.swipe.isRefreshing = loading
            }

            false -> {
                loadingDialog?.dismiss()
                binding.swipe.isRefreshing = loading
            }
        }
    }

    override fun onClick(v: View, data: Data) {
        startActivity(Intent(this, ChatActivity::class.java).putExtra("data", data))
    }

}