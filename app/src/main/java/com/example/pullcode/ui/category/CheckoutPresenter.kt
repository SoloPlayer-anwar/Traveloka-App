package com.example.pullcode.ui.category

import com.example.pullcode.network.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CheckoutPresenter(private val view: CheckoutContract.View): CheckoutContract.Presenter {

    private val mCompositeDisposable: CompositeDisposable?
    init {
        mCompositeDisposable = CompositeDisposable()
    }
    override fun submitDestination(
        userId: Int,
        quantity: Int,
        total: Int,
        status:String,
        destinationId: Int,
        checkin: String,
        namaBandara:String,
        provinsi:String,
        jamTerbang:String,
        picturePesawat:String,
        pictureProduct:String,
        name:String,
        rating:Double
    ) {
        view.showLoading(true)
        val disposable = HttpClient.getInstance().getApi()!!.checkoutDestination(
            userId, quantity, total, "PENDING", destinationId,checkin,namaBandara, provinsi, jamTerbang,picturePesawat,pictureProduct,name, rating
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                when(it.meta?.status.equals("success", true)) {
                    true -> {
                        it.data?.let { it1 -> view.checkoutDestination(it1) }
                        view.showLoading(false)
                    }

                    false -> {
                        it.meta?.let { it1 -> view.checkoutFailure(it1.message) }
                        view.showLoading(false)
                    }
                }
            }, {
                view.checkoutFailure(it.message.toString())
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