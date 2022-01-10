package com.example.pullcode.ui.dashboard.explore

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
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.pullcode.R
import com.example.pullcode.databinding.FragmentExploreBinding
import com.example.pullcode.response.explore.Data
import com.example.pullcode.response.explore.ExploreResponse


class ExploreFragment : Fragment(), ExploreContract.View, AdapterExplore.ItemAdapterCallback, AdapterVideo.ItemAdapterCallback {
    private lateinit var binding: FragmentExploreBinding
    private lateinit var presenter: ExplorePresenter
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
        binding = FragmentExploreBinding.inflate(layoutInflater)
        return (binding.root)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ExploreFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = ExplorePresenter(this)
        presenter.getExplore()
        initLoading()

        val imageList = ArrayList<SlideModel>()

        imageList.add(SlideModel("https://firebasestorage.googleapis.com/v0/b/uniskabanjarmasinapplication.appspot.com/o/travel-banner2.jpeg?alt=media&token=56a7a1fc-781d-4346-8c38-c3fbf7249e47", ScaleTypes.CENTER_CROP, ))
        imageList.add(SlideModel("https://firebasestorage.googleapis.com/v0/b/uniskabanjarmasinapplication.appspot.com/o/travel-banner3.jpg?alt=media&token=2ea89636-aa06-4164-98d9-72bc23c140af", ScaleTypes.CENTER_CROP, ))
        imageList.add(SlideModel("https://firebasestorage.googleapis.com/v0/b/uniskabanjarmasinapplication.appspot.com/o/travel-banner4.jpg?alt=media&token=556caeb6-947c-4b2a-95fd-e89fd31b92dc", ScaleTypes.CENTER_CROP, ))

        binding.imageSlider.setImageList(imageList)

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

    override fun exploreSuccess(exploreResponse: ExploreResponse) {
        val adapter = AdapterExplore(exploreResponse.data, this)
        binding.rvUser.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvUser.adapter = adapter

        val videoAdapter = AdapterVideo(exploreResponse.data, this)
        binding.rvDestination.layoutManager = LinearLayoutManager(context)
        binding.rvDestination.adapter = videoAdapter
    }

    override fun exploreFailure(message: String) {
        Toast.makeText(context, message,Toast.LENGTH_SHORT).show()
        Log.d("message", message)
    }

    override fun showLoading(loading: Boolean) {
        when(loading) {
            true -> {
                loadingDialog?.show()
                loading
            }
            false ->{
                loadingDialog?.dismiss()
                loading
            }
        }
    }

    override fun onClick(v: View, data: Data) {
        startActivity(Intent(context, VideoActivity::class.java).putExtra("data", data))
    }
}