package com.example.pullcode.ui.dashboard.profile

import android.app.Activity
import android.app.Dialog
import android.app.Presentation
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.pullcode.R
import com.example.pullcode.Traveling
import com.example.pullcode.databinding.FragmentProfileBinding
import com.example.pullcode.response.sign.SignResponse
import com.example.pullcode.response.sign.User
import com.example.pullcode.ui.dashboard.BottomNavigationActivity
import com.example.pullcode.ui.login.LoginActivity
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.gson.Gson

class ProfileFragment : Fragment(), ProfileContract.View {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var presenter: ProfilePresenter
    private var loadingDialog: Dialog? = null
    var filePath: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return (binding.root)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            ProfileFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = ProfilePresenter(this)
        val user = Traveling.getApp().getUser()
        val userResponse = Gson().fromJson(user, User::class.java)

        initLoading()
        binding.tvLogout.setOnClickListener {
            Traveling.getApp().clearToken()
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
            activity?.finishAffinity()
        }

        binding.tvName.text = userResponse.name
        binding.tvEmail.text = userResponse.email
        binding.tvPhone.text = userResponse.phone
        binding.tvAddress.text = userResponse.address
        binding.tvCity.text = userResponse.city

        binding.ivClickProfile.setOnClickListener {
            ImagePicker.with(this)
                .cameraOnly()
                .start()
            binding.tvSave.visibility = View.VISIBLE
        }

        binding.tvSave.setOnClickListener {
            presenter.submitPhotoProfile(
                filePath!!
            )
        }

        Glide.with(this)
            .load(userResponse.profile_photo_path)
            .placeholder(R.drawable.animation)
            .into(binding.ivProfile)

        binding.editProfile.setOnClickListener {

        }
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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (resultCode) {
            Activity.RESULT_OK -> {
                filePath = data?.data

                Glide.with(requireActivity())
                    .load(filePath)
                    .into(binding.ivProfile)
            }
            ImagePicker.RESULT_ERROR -> {
                Toast.makeText(context, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(context, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun photoProfileSuccess(signResponse: SignResponse) {
        Traveling.getApp().setToken(signResponse.access_token)
        val gson =  Gson()
        val json = gson.toJson(signResponse.user)
        Traveling.getApp().setUser(json)
        startActivity(Intent(context, BottomNavigationActivity::class.java))
        Toast.makeText(context, "Profile berhasil di update",Toast.LENGTH_LONG).show()
    }

    override fun photoFailure(message: String) {
       startActivity(Intent(context, BottomNavigationActivity::class.java))
        Toast.makeText(context, "Profile berhasil di update",Toast.LENGTH_LONG).show()
    }

    override fun showLoading(loading: Boolean) {
        when(loading) {
            true -> {
                loadingDialog?.show()
                loading
            }

            false -> {
                loadingDialog?.dismiss()
                loading
            }
        }
    }
}