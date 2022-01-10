package com.example.pullcode.ui.dashboard.swab

import android.annotation.SuppressLint
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.pullcode.R
import com.example.pullcode.databinding.ActivitySuccessQrcodeBinding
import com.example.pullcode.response.transaction.Data
import com.example.pullcode.utils.Helpers.formatPrice

class SuccessQrcodeActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySuccessQrcodeBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuccessQrcodeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val data = intent.getParcelableExtra<Data>("data")

        Glide.with(this)
            .load(data?.destinasi?.picturePath)
            .placeholder(R.drawable.animation)
            .into(binding.ivProduct)

        binding.tvName.text = data?.destinasi?.name
        binding.ratingBar.rating = data?.destinasi?.rate?.toFloat() ?: 0f

        Glide.with(this)
            .load(data?.picturePesawat)
            .placeholder(R.drawable.animation)
            .into(binding.ivPesawat)

        binding.tvTanggal.text = data?.checkin
        binding.tvTotal.formatPrice(data?.total.toString())
        binding.tvQuantity.text = "${data?.quantity} orang"
        binding.tvUser.text = data?.user?.name
        binding.tvNameBandara.text = data?.namaBandara
        binding.tvTujuan.text = data?.destinasi?.category
        binding.back.setOnClickListener {
            finish()
        }

        binding.btnqrCode.setOnClickListener {
            showDialog("Silahkan melakukan scanning di bandara yang anda pilih")
        }
    }

    private fun showDialog(title:String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.item_barcode)
        dialog.window?.setBackgroundDrawableResource(R.color.tsp)
        val tvDesc = dialog.findViewById<TextView>(R.id.tvDesc)
        tvDesc.text = title

        val btnClose =  dialog.findViewById<Button>(R.id.btnTutup)
        btnClose.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}