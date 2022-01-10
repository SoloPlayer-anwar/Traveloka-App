package com.example.pullcode.airport

import com.example.pullcode.network.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class BandaraPresenter(private val view: BandaraContract.View): BandaraContract.Presenter {
    private val mCompositeDisposable : CompositeDisposable?
    init {
        mCompositeDisposable = CompositeDisposable()
    }

    override fun submitAirport() {
        val disposable = HttpClient.getInstance().getApi()!!.airport()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                when(it.meta.status.equals("success", true)) {
                    true -> {
                        view.airportSuccess(it)
                    }

                    false -> {
                        view.airportFailure(it.meta.message)
                    }
                }
            },{
                view.airportFailure(it.message.toString())

            })

        mCompositeDisposable?.add(disposable)
    }

    override fun subscribe() {

    }

    override fun unSubscribe() {
        mCompositeDisposable!!.clear()
    }
}