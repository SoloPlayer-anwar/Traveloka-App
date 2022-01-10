package com.example.pullcode.ui.keranjang

import com.example.pullcode.network.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PendingPresenter(private val view: PendingContract.View):PendingContract.Presenter{
    private val mCompositeDisposable: CompositeDisposable?
    init {
        mCompositeDisposable = CompositeDisposable()
    }

    override fun submitPending(status:String) {
        view.showLoading(true)
        val disposable = HttpClient.getInstance().getApi()!!.transactionPending(status)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                when(it.meta?.status.equals("success", true)) {
                    true -> {
                        it.data?.let { it1 -> view.transactionPending(it1) }
                        view.showLoading(false)
                    }

                    false -> {
                        it.meta?.let { it1 -> view.transactionFailure(it1.message) }
                        view.showLoading(false)
                    }
                }
            }, {
                view.transactionFailure(it.message.toString())
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