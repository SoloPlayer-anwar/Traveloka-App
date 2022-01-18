package com.example.pullcode.ui.dashboard.swab

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pullcode.R
import com.example.pullcode.databinding.FragmentSwabBinding
import com.example.pullcode.response.transaction.Data
import com.example.pullcode.response.transaction.TransactionResponse
import com.example.pullcode.ui.keranjang.PendingContract
import com.example.pullcode.ui.keranjang.PendingPresenter

class SwabFragment : Fragment(), PendingContract.View, AdapterSuccess.ItemAdapterCallback {
    private lateinit var binding: FragmentSwabBinding
    private lateinit var presenter: PendingPresenter
    var loadingDialog : Dialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSwabBinding.inflate(layoutInflater)
        return (binding.root)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SwabFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = PendingPresenter(this)
        presenter.submitPending("SUCCESS")
        initLoading()
        binding.swipe.setOnRefreshListener {
            presenter.submitPending("SUCCESS")
        }
    }

    private fun initLoading() {
        loadingDialog = Dialog(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.loading_dialog, null)

        loadingDialog.let {
            it?.setContentView(dialogView)
            it?.setCancelable(false)
            it?.window?.setBackgroundDrawableResource(R.color.tsp)
        }
    }

    override fun transactionPending(transactionResponse: TransactionResponse) {

        if(transactionResponse.data.isNullOrEmpty()) {
            binding.rvSuccess.visibility = View.INVISIBLE
            binding.lySuccess.visibility = View.VISIBLE
        }else {
            val adapter = AdapterSuccess(transactionResponse.data,this)
            binding.rvSuccess.layoutManager = LinearLayoutManager(context)
            binding.rvSuccess.adapter = adapter
        }


    }

    override fun transactionFailure(message: String) {
        Toast.makeText(context, message,Toast.LENGTH_SHORT).show()
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
        startActivity(Intent(context, SuccessQrcodeActivity::class.java).putExtra("data",data))
    }

}