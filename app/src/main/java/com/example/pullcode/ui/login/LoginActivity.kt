package com.example.pullcode.ui.login

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pullcode.R
import com.example.pullcode.Traveling
import com.example.pullcode.databinding.ActivityLoginBinding
import com.example.pullcode.response.sign.SignResponse
import com.example.pullcode.ui.dashboard.BottomNavigationActivity
import com.example.pullcode.ui.register.RegisterActivity
import com.google.gson.Gson

class LoginActivity : AppCompatActivity(), LoginContract.View {
    private lateinit var binding: ActivityLoginBinding
    lateinit var presenter: LoginPresenter
    var loadingDialog : Dialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = LoginPresenter(this)
        initLoading()

        binding.register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        when {
            !Traveling.getApp().getToken().isNullOrEmpty() -> {
                val intent = Intent(this,BottomNavigationActivity::class.java)
                startActivity(intent)
                finishAffinity()
            }
        }


        binding.btnSuccess.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            when {
                email.isEmpty() -> {
                    binding.etEmail.error = "isi alamat email"
                    binding.etEmail.requestFocus()
                }

                password.isEmpty() -> {
                    binding.etPassword.error = "isi password anda"
                    binding.etPassword.requestFocus()
                }
                else -> {
                    presenter.submitLogin(
                        email,
                        password
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

    override fun loginSuccess(signResponse: SignResponse) {
        Traveling.getApp().setToken(signResponse.access_token)
        val gson = Gson()
        val json = gson.toJson(signResponse.user)
        Traveling.getApp().setUser(json)
        val intent = Intent(this,BottomNavigationActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }

    override fun loginFailure(message: String) {
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