package com.example.pullcode.ui.dashboard.explore

import com.example.pullcode.network.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ExplorePresenter(private val view: ExploreContract.View): ExploreContract.Presenter {
    private val mCompositeDisposable: CompositeDisposable?
    init {
        mCompositeDisposable = CompositeDisposable()
    }

    override fun getExplore() {
        view.showLoading(true)
        val disposable = HttpClient.getInstance().getApi()!!.explore()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                when(it.meta.status.equals("success", true)) {
                    true -> {
                        view.exploreSuccess(it)
                        view.showLoading(false)
                    }

                    false -> {
                        view.exploreFailure(it.meta.message)
                        view.showLoading(false)
                    }
                }
            }, {
                view.exploreFailure(it.message.toString())
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