package com.example.pullcode.ui.category

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.pullcode.R
import com.example.pullcode.databinding.ActivityDestinasiDetailBinding
import com.example.pullcode.response.destinasi.Data
import com.example.pullcode.ui.chat.ChatActivity
import com.example.pullcode.utils.Helpers.formatPrice
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.constants.Style
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView

class DetailDestinasiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDestinasiDetailBinding
    private var mapView: MapView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDestinasiDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Mapbox.getInstance(applicationContext, getString(R.string.mapbox_access_token))
        val des = intent.getParcelableExtra<Data>("des")

        binding.ivPesan.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java)
                .putExtra("des", des)
            startActivity(intent)
        }

        mapView = binding.mapView
        mapView!!.onCreate(savedInstanceState)
        mapView!!.getMapAsync {
            it.setStyle(Style.TRAFFIC_NIGHT)

            val lat = des?.lat
            val long = des?.long
            val location = LatLng(lat!!, long!!)
            val position = CameraPosition.Builder()
                .target(LatLng(location))
                .zoom(9.0)
                .bearing(9.0)
                .tilt(10.0)
                .build()

            it.animateCamera(CameraUpdateFactory.newCameraPosition(position), 2000)
            it.addMarker(MarkerOptions().setPosition(location).title(des.category))
        }

        Glide.with(this)
            .load(des?.picturePath)
            .into(binding.ivProduct)

        Glide.with(this)
            .load("https://firebasestorage.googleapis.com/v0/b/uniskabanjarmasinapplication.appspot.com/o/1%20(4).jpg?alt=media&token=111bffa6-a1ff-41b4-ac3c-a27791ce017b")
            .into(binding.ivOrang)

        binding.back.setOnClickListener {
            finish()
        }

        binding.tvName.text = des?.name
        binding.tvCategory.text = des?.category
        binding.ratingFloat.rating = des?.rate?.toFloat() ?: 0f
        binding.ratingInt.text = des?.rate.toString()
        binding.tvAddress.text = des?.place
        binding.tvDesc.text = des?.description
        binding.tvPrice.formatPrice(des?.price.toString())

        binding.btnBooking.setOnClickListener {
            val des = intent.getParcelableExtra<Data>("des")
            val intent = Intent(this, CheckoutDestinationActivity::class.java)
                .putExtra("des", des)
            startActivity(intent)
            finish()
        }

        binding.map.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(des?.map)))
        }

        binding.arah.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(des?.map)))
        }

    }

}