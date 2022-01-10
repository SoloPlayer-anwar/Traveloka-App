package com.example.pullcode.ui.register

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.pullcode.R
import com.example.pullcode.Traveling
import com.example.pullcode.databinding.ActivityRegisterBinding
import com.example.pullcode.response.sign.SignResponse
import com.google.gson.Gson
import kotlin.math.sign

class RegisterActivity : AppCompatActivity(), RegisterContract.View {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var presenter: RegisterPresenter
    var loadingDialog : Dialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = RegisterPresenter(this)
        initLoading()

        binding.btnRegister.setOnClickListener {
            val fullName = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val address = binding.etAddress.text.toString()
            val phone = binding.etPhone.text.toString()
            val city = binding.etCity.text.toString()

            when {
                fullName.isEmpty() -> {
                    binding.etName.error = "isi nama lengkap"
                    binding.etName.requestFocus()
                }

                email.isEmpty() -> {
                    binding.etEmail.error = "isi email anda"
                    binding.etEmail.requestFocus()
                }

                password.isEmpty() -> {
                    binding.etPassword.error = "isi password anda"
                    binding.etPassword.requestFocus()
                }

                address.isEmpty() -> {
                    binding.etAddress.error = "isi alamat anda"
                    binding.etAddress.requestFocus()
                }

                phone.isEmpty() -> {
                    binding.etPhone.error = "isi phone anda"
                    binding.etPhone.requestFocus()
                }

                city.isEmpty() -> {
                    binding.etCity.error = "isi kota anda"
                    binding.etCity.requestFocus()
                }
                else -> {
                    presenter.submitRegister(
                        fullName,
                        email,
                        password,
                        password,
                        address,
                        phone, city
                    )
                }
            }
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

    override fun registerSuccess(signResponse: SignResponse) {
        Traveling.getApp().setToken(signResponse.access_token)

        val gson = Gson()
        val json = gson.toJson(signResponse.user)
        Traveling.getApp().setUser(json)

        val intent = Intent(this, RegisterSuccessActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }

    override fun registerFailure(message: String) {
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