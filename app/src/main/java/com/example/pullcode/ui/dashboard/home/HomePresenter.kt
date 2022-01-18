package com.example.pullcode.ui.dashboard.home

import com.example.pullcode.network.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomePresenter(private val view: HomeContract.View):HomeContract.Presenter {
    private val mCompositeDisposable : CompositeDisposable?

    init {
        mCompositeDisposable = CompositeDisposable()
    }

    override fun getHome() {
        view.showLoading(true)
        val disposable = HttpClient.getInstance().getApi()!!.product()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                when(it.meta?.status.equals("success", true)){
                    true -> {
                        it.data?.let { it1 -> view.homeSuccess(it1) }
                        view.showLoading(false)
                    }

                    false -> {
                        it.meta?.let { it1 -> view.homeFailure(it1.message) }
                        view.showLoading(false)
                    }
                }
            }, {
                view.homeFailure(it.message.toString())
                view.showLoading(false)
            })
        mCompositeDisposable!!.add(disposable)
    }

    override fun getDestinasi() {
        view.showLoading(true)
        val disposable = HttpClient.getInstance().getApi()!!.destination()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                when(it.meta?.status.equals("success",true)) {
                    true -> {
                        it.data?.let { it1 -> view.destinationSuccess(it1) }
                        view.showLoading(false)
                    }

                    false -> {
                        it.meta?.let { it1 -> view.destinationFailure(it1.message) }
                        view.showLoading(false)
                    }
                }
            }, {
                view.destinationFailure(it.message.toString())
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