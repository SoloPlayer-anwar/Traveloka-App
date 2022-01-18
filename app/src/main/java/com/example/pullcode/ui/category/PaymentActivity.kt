package com.example.pullcode.ui.category

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pullcode.databinding.ActivityPaymentBinding
import com.example.pullcode.response.checkout.CheckoutResponse
import com.example.pullcode.ui.dashboard.BottomNavigationActivity
import com.example.pullcode.ui.keranjang.PendingActivity

class PaymentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnProduct.setOnClickListener {
            startActivity(Intent(this,PendingActivity::class.java))
            finish()
        }

        binding.btnBack.setOnClickListener {
            startActivity(Intent(this, BottomNavigationActivity::class.java))
            finish()
        }
    }
}