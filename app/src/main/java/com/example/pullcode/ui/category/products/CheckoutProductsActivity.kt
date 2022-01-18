package com.example.pullcode.ui.category.products

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.pullcode.R
import com.example.pullcode.Traveling
import com.example.pullcode.databinding.ActivityCheckoutProductsBinding
import com.example.pullcode.response.checkout.CheckoutResponse
import com.example.pullcode.response.product.Data
import com.example.pullcode.response.sign.User
import com.example.pullcode.ui.category.CheckoutContract
import com.example.pullcode.ui.category.PaymentActivity
import com.example.pullcode.utils.Helpers.formatPrice
import com.google.gson.Gson
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class CheckoutProductsActivity : AppCompatActivity(),CheckoutProductContract.View {
    private lateinit var binding: ActivityCheckoutProductsBinding
    private lateinit var presenter: CheckoutProductPresenter
    var loadingDialog : Dialog? = null
    var filePath:String? = null
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = CheckoutProductPresenter(this)
        initLoading()

        val data = intent.getParcelableExtra<Data>("data")
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
            binding.tvTotal.formatPrice((qty * data?.price!!).toString())

        }

        binding.ivMines.setOnClickListener {
            var qty = binding.tvQuantity.text.toString().toInt()
            if(qty > 1) qty -= 1
            binding.tvQuantity.text = qty.toString()
            binding.quantity.text = "$qty x"
            binding.tvTotal.formatPrice((qty * data?.price!!).toString())
        }

        binding.tvPrice.formatPrice(data?.price.toString())
        binding.tvCategory.text = data?.name
        binding.tvPlace.text = data?.place
        Glide.with(this )
            .load(data?.picturePath)
            .placeholder(R.drawable.animation)
            .into(binding.ivProduct)
        filePath = data?.picturePath

        binding.tvName.text = data?.name
        binding.ratingCheckout.rating = data?.rate?.toFloat() ?: 0f
        binding.tvExpired.text = "${data?.expired} Expired"
        binding.tvUser.text = userResponse.name
        binding.tvCity.text = userResponse.city
        binding.tvAddress.text = userResponse.address
        binding.tvTotal.formatPrice(data?.price.toString())

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

        binding.btnCheckout.setOnClickListener {


            val userId = userResponse.id
            val quantity = binding.tvQuantity.text.toString().toInt()
            val total = quantity * data?.price!!
            val productId = data.id
            val checkin = binding.tvTanggal.text
            val productPicture = filePath
            val name = data.name
            val rating = data.rate

            presenter.submitProduct(
                userId,
                quantity,
                total,
                "PENDING",
                productId!!,
                checkin.toString(),
                productPicture.toString(),
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

    override fun checkoutProduct(checkoutResponse: CheckoutResponse) {
        val intent = Intent(this, PaymentActivity::class.java)
        startActivity(intent)
        finishAffinity()
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(checkoutResponse.paymentUrl)))
    }

    override fun productFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
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