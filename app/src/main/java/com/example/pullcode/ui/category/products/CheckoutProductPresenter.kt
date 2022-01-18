package com.example.pullcode.ui.category.products

import com.example.pullcode.network.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CheckoutProductPresenter(private val view: CheckoutProductContract.View):CheckoutProductContract.Presenter {
    private val mCompositeDisposable : CompositeDisposable?
    init {
        mCompositeDisposable = CompositeDisposable()
    }
    override fun submitProduct(
        userId: Int,
        quantity: Int,
        total: Int,
        status: String,
        productId: Int,
        checkin: String,
        pictureProduct:String,
        name:String,
        rating:Double
    ) {
        view.showLoading(true)
        val disposable = HttpClient.getInstance().getApi()!!.checkoutProduct(
            userId,quantity,total,status,productId,checkin,pictureProduct, name, rating
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                when(it.meta?.status.equals("success",true)) {
                    true -> {
                        it.data?.let { it1 -> view.checkoutProduct(it1) }
                                view.showLoading(false)
                    }

                    false -> {
                        it.meta?.let { it1 -> view.productFailure(it1.message) }
                                view.showLoading(false)
                    }
                }
            }, {
                view.productFailure(it.message.toString())
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