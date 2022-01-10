package com.example.pullcode.ui.category

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pullcode.databinding.ActivityPaymentBinding
import com.example.pullcode.response.checkout.CheckoutResponse

class PaymentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}