package com.example.pullcode.ui.register

import com.example.pullcode.network.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class RegisterPresenter(private val view: RegisterContract.View):RegisterContract.Presenter {

    private val mCompositeDisposable: CompositeDisposable?
    init {
        mCompositeDisposable = CompositeDisposable()
    }

    override fun submitRegister(
        name: String,
        email: String,
        password: String,
        passwordConfirm: String,
        address: String,
        phone: String,
        city: String,
    ) {
        view.showLoading(true)
        val disposable = HttpClient.getInstance().getApi()!!.register(
            name, email, password, passwordConfirm, address, phone, city
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                when(it.meta?.status.equals("success", true)) {
                    true -> {
                        it.data?.let { it1 -> view.registerSuccess(it1) }
                        view.showLoading(false)
                    }

                    false -> {
                        it.meta?.let { it1 -> view.registerFailure(it1.message) }
                        view.showLoading(false)
                    }
                }
            }, {
                view.registerFailure(it.message.toString())
                view.showLoading(false)
            })
        mCompositeDisposable!!.add(disposable)
    }

    override fun subscribe() {

    }

    override fun unSubscribe() {
        mCompositeDisposable!!.clear()
    }
}