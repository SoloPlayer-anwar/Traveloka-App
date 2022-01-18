package com.example.pullcode.ui.category

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.pullcode.R
import com.example.pullcode.Traveling
import com.example.pullcode.airport.BandaraActivity
import com.example.pullcode.databinding.ActivityCheckoutDestinationBinding
import com.example.pullcode.response.bandara.Data
import com.example.pullcode.response.checkout.CheckoutResponse
import com.example.pullcode.response.sign.User
import com.example.pullcode.utils.Helpers.formatPrice
import com.google.gson.Gson
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class CheckoutDestinationActivity : AppCompatActivity(), CheckoutContract.View {

    private lateinit var binding: ActivityCheckoutDestinationBinding
    var loadingDialog: Dialog? = null
    private lateinit var presenter: CheckoutPresenter
    var filePath: String? = null
    var fileProduct:String? = null
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutDestinationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = CheckoutPresenter(this)
        initLoading()

        val des = intent.getParcelableExtra<com.example.pullcode.response.destinasi.Data>("des")
        val user = Traveling.getApp().getUser()
        val userResponse = Gson().fromJson(user, User::class.java)


        binding.back.setOnClickListener {
            finish()
        }

        binding.ivPlus.setOnClickListener {
            var qty = binding.tvQuantity.text.toString().toInt()
            qty += 1
            binding.tvQuantity.text = qty.toString()
            binding.quantity.text = "$qty x"
            binding.tvTotal.formatPrice((qty * des?.price!!).toString())

        }

        binding.ivMines.setOnClickListener {
            var qty = binding.tvQuantity.text.toString().toInt()
            if(qty > 1) qty -= 1
            binding.tvQuantity.text = qty.toString()
            binding.quantity.text = "$qty x"
            binding.tvTotal.formatPrice((qty * des?.price!!).toString())
        }

        binding.tvPrice.formatPrice(des?.price.toString())
        binding.tvCategory.text = des?.category
        binding.tvPlace.text = des?.place
        Glide.with(this )
            .load(des?.picturePath)
            .placeholder(R.drawable.animation)
            .into(binding.ivProduct)
        fileProduct = des?.picturePath

        binding.tvName.text = des?.name
        binding.ratingCheckout.rating = des?.rate?.toFloat() ?: 0f
        binding.tvExpired.text = "${des?.expired} Expired"
        binding.tvUser.text = userResponse.name
        binding.tvTotal.formatPrice(des?.price.toString())

        val myCalendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener{ _, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val sdf = DateFormat.getDateInstance(SimpleDateFormat.FULL)
            binding.tvTanggal.text = sdf.format(myCalendar.time).toString()
        }

        binding.btnTanggal.setOnClickListener {
            DatePickerDialog(this,
                datePicker,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }


        val resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK){
                val data = result.data?.getParcelableExtra<Data>("data")
                binding.tvProvinsi.text = data?.provinsi
                binding.tvNameBandara.text = data?.namaBandara
                binding.provinsi.text = data?.provinsi
                binding.bandara.text = data?.namaBandara
                Glide.with(this)
                    .load(data?.picturePesawat)
                    .into(binding.ivPesawat)
                filePath = data?.picturePesawat
                binding.tvJam.text = data?.jamTerbang
            }
        }

        binding.btnSetData.setOnClickListener {
            val intent = Intent(this, BandaraActivity::class.java)
            resultLauncher.launch(intent)
        }


        binding.btnCheckout.setOnClickListener {


            val userId = userResponse.id
            val quantity = binding.tvQuantity.text.toString().toInt()
            val total = quantity * des?.price!!
            val destinationId = des.id
            val checkin = binding.tvTanggal.text
            val namaBandara = binding.tvNameBandara.text
            val provinsi = binding.tvProvinsi.text
            val jamTerbang = binding.tvJam.text
            val picturePesawat = filePath
            val pictureProduct = fileProduct
            val name = des.name
            val rating = des.rate

            presenter.submitDestination(
                userId,
                quantity,
                total,
                "PENDING",
                destinationId!!,
                checkin.toString(),
                namaBandara.toString(),
                provinsi.toString(),
                jamTerbang.toString(),
                picturePesawat!!,
                pictureProduct!!,
                name.toString(),
                rating!!
            )
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

    override fun checkoutDestination(checkoutResponse: CheckoutResponse) {
       val intent = Intent(this, PaymentActivity::class.java)
        startActivity(intent)
        finishAffinity()
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(checkoutResponse.paymentUrl)))
    }

    override fun checkoutFailure(message: String) {
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show()
    }

    override fun showLoading(loading: Boolean) {
        when(loading) {
            true -> {
                loadingDialog?.show()
            }

            false -> {
                loadingDialog?.dismiss()
            }
        }
    }

}