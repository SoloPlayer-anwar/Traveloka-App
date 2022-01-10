package com.example.pullcode.ui.dashboard.home

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.pullcode.R
import com.example.pullcode.databinding.FragmentHomeBinding
import com.example.pullcode.response.destinasi.DestinasiResponse
import com.example.pullcode.response.product.Data
import com.example.pullcode.response.product.ProductResponse
import com.example.pullcode.ui.category.AdapterDestinasi
import com.example.pullcode.ui.category.DetailDestinasiActivity
import com.example.pullcode.ui.chat.ChatActivity
import com.example.pullcode.ui.keranjang.PendingActivity


class HomeFragment : Fragment(), HomeContract.View, HomeAdapter.ItemAdapterCallback, AdapterDestinasi.OnClickAdapter{
    private lateinit var binding: FragmentHomeBinding
    lateinit var presenter: HomePresenter
    private var listRecommended: ArrayList<Data> = ArrayList()
    private var listPopuler: ArrayList<Data> = ArrayList()
    private var listNew: ArrayList<Data> = ArrayList()
    private var ResList: ArrayList<Data> = ArrayList()
    var loadingDialog : Dialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return (binding.root)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = HomePresenter(this)
        initLoading()
        presenter.getHome(24)
        presenter.getDestinasi()


        binding.ivKeranjang.setOnClickListener {
            val intent = Intent(context, PendingActivity::class.java)
            startActivity(intent)
        }

        binding.ivChat.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)
            startActivity(intent)
        }

        val imageList = ArrayList<SlideModel>()

        imageList.add(SlideModel("https://firebasestorage.googleapis.com/v0/b/uniskabanjarmasinapplication.appspot.com/o/traveloka3.jpg?alt=media&token=7e7eb43e-7256-48fb-aee7-218b59e44bdd", ScaleTypes.CENTER_CROP, ))
        imageList.add(SlideModel("https://firebasestorage.googleapis.com/v0/b/uniskabanjarmasinapplication.appspot.com/o/2.jpg?alt=media&token=56dac442-0483-49af-a722-3cc2baf6ef7c", ScaleTypes.CENTER_CROP, ))
        imageList.add(SlideModel("https://firebasestorage.googleapis.com/v0/b/uniskabanjarmasinapplication.appspot.com/o/1.jpg?alt=media&token=2da75f87-0bc6-457c-8cc3-556af0f76d2a", ScaleTypes.CENTER_CROP, ))

        binding.imageSlider.setImageList(imageList)

        Glide.with(this)
            .load("https://firebasestorage.googleapis.com/v0/b/uniskabanjarmasinapplication.appspot.com/o/tes2.jpg?alt=media&token=e14f18f2-f850-41ae-bf6a-b0cd7d29b800")
            .placeholder(R.drawable.animation)
            .into(binding.ivImage)

        Glide.with(this)
            .load("https://firebasestorage.googleapis.com/v0/b/uniskabanjarmasinapplication.appspot.com/o/tes3.jpeg?alt=media&token=eba1cd40-57f5-4bd1-8866-fc199694ae7f")
            .placeholder(R.drawable.animation)
            .centerCrop()
            .into(binding.ivImageFood)
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

    override fun homeSuccess(productResponse: ProductResponse) {
        for ( a in productResponse.data.indices)
        {
            val items: List<String> = productResponse.data[a].category?.split(",") ?: ArrayList()
            for(x in items.indices)
            {
                when {
                    items[x].equals("Recommended", true) -> {
                        listRecommended.add(productResponse.data[a])
                    }

                    items[x].equals("Populer", true) -> {
                        listPopuler.add(productResponse.data[a])
                    }

                    items[x].equals("New Room", true) -> {
                        listNew.add(productResponse.data[a])
                    }


                    items[x].equals("Resto", true) -> {
                        ResList.add(productResponse.data[a])
                    }
                }
            }
        }

        binding.rvRecomen.setHasFixedSize(true)
        val adapterRecom = HomeAdapter(listRecommended, this)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvRecomen.layoutManager = layoutManager
        binding.rvRecomen.adapter = adapterRecom

        val adapterPop = HomeAdapter(listPopuler, this)
        binding.rvPopuler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvPopuler.adapter = adapterPop

        val adapterNew = HomeAdapter(listNew, this)
        binding.rvBaru.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvBaru.adapter = adapterNew


        val adapterResto = HomeAdapter(ResList, this)
        binding.rvResto.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvResto.adapter = adapterResto
    }

    override fun destinationSuccess(destinasiResponse: DestinasiResponse) {

        val adapter = AdapterDestinasi(destinasiResponse.data, this)
        binding.rvDestinasi.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvDestinasi.adapter = adapter
    }

    override fun homeFailure(message: String) {
        Toast.makeText(context, message,Toast.LENGTH_SHORT).show()
        Log.d("context", message)
    }

    override fun destinationFailure(message: String) {
        Toast.makeText(context, message,Toast.LENGTH_SHORT).show()
    }

    override fun showLoading(loading: Boolean) {
        when(loading) {
            true -> {
                binding.swipe.setOnRefreshListener {
                    loadingDialog!!.show()
                    binding.swipe.isRefreshing = true
                }
            }

            false -> {
                binding.swipe.setOnRefreshListener {
                    loadingDialog!!.dismiss()
                    binding.swipe.isRefreshing = false
                }
            }
        }
    }

    override fun onCLick(v: View, data: Data) {
        Toast.makeText(context, "tes", Toast.LENGTH_SHORT).show()
    }

    override fun onCLick(v: View, data: com.example.pullcode.response.destinasi.Data) {
        val intent = Intent(context, DetailDestinasiActivity::class.java)
            .putExtra("des", data)
        startActivity(intent)
    }
}