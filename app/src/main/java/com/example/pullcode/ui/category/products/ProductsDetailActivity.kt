package com.example.pullcode.ui.category.products

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.pullcode.R
import com.example.pullcode.databinding.ActivityProductsDetailBinding
import com.example.pullcode.response.product.Data
import com.example.pullcode.ui.category.CheckoutDestinationActivity
import com.example.pullcode.ui.chat.ChatActivity
import com.example.pullcode.utils.Helpers.formatPrice
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.constants.Style
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView

class ProductsDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductsDetailBinding
    private var mapView: MapView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Mapbox.getInstance(applicationContext, getString(R.string.mapbox_access_token))
        val data = intent.getParcelableExtra<Data>("data")

        binding.ivPesan.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java)
                .putExtra("product", data)
            startActivity(intent)
        }

        mapView = binding.mapView
        mapView!!.onCreate(savedInstanceState)
        mapView!!.getMapAsync {
            it.setStyle(Style.TRAFFIC_NIGHT)

            val lat = data?.lat
            val long = data?.long
            val location = LatLng(lat!!, long!!)
            val position = CameraPosition.Builder()
                .target(LatLng(location))
                .zoom(9.0)
                .bearing(9.0)
                .tilt(10.0)
                .build()

            it.animateCamera(CameraUpdateFactory.newCameraPosition(position), 2000)
            it.addMarker(MarkerOptions().setPosition(location).title(data.category))
        }

        Glide.with(this)
            .load(data?.picturePath)
            .into(binding.ivProduct)

        Glide.with(this)
            .load("https://firebasestorage.googleapis.com/v0/b/uniskabanjarmasinapplication.appspot.com/o/1%20(1).jpg?alt=media&token=546e7aa6-f549-4439-b7bc-fe289149c878")
            .into(binding.ivOrang)

        binding.back.setOnClickListener {
            finish()
        }

        binding.tvName.text = data?.name
        binding.tvCategory.text = data?.category
        binding.ratingFloat.rating = data?.rate?.toFloat() ?: 0f
        binding.ratingInt.text = data?.rate.toString()
        binding.tvAddress.text = data?.place
        binding.tvDesc.text = data?.description
        binding.tvPrice.formatPrice(data?.price.toString())

        binding.btnBooking.setOnClickListener {
            val data = intent.getParcelableExtra<Data>("data")
            val intent = Intent(this, CheckoutProductsActivity::class.java)
                .putExtra("data", data)
            startActivity(intent)
            finish()
        }

        binding.map.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(data?.map)))
        }

        binding.arah.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(data?.map)))
        }

    }
}