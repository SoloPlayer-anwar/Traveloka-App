package com.example.pullcode.ui.dashboard.profile

import android.net.Uri
import com.example.pullcode.network.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class ProfilePresenter(private val view: ProfileContract.View):ProfileContract.Presenter {
    private val mCompositeDisposable: CompositeDisposable?
    init {
        mCompositeDisposable = CompositeDisposable()
    }
    override fun submitPhotoProfile(filePath: Uri) {
        val profileImageFile = File(filePath.path)
        val profileImageRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), profileImageFile)
        val profileImageParams = MultipartBody.Part.createFormData("file", profileImageFile.name, profileImageRequestBody)

        view.showLoading(true)
        val disposable = HttpClient.getInstance().getApi()!!.photoRegister(
            profileImageParams
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                when(it.meta?.status.equals("success", true)) {
                    true -> {
                        it.data?.let { it1 -> view.photoProfileSuccess(it1) }
                        view.showLoading(false)
                    }

                    false -> {
                        it.meta?.let { it1 -> view.photoFailure(it1.message) }
                        view.showLoading(false)
                    }
                }
            }, {
                view.photoFailure(it.message.toString())
                view.showLoading(false)
            })
    }

    override fun subscribe() {
        TODO("Not yet implemented")
    }

    override fun unSubscribe() {
        mCompositeDisposable?.clear()
    }
}