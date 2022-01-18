package com.example.pullcode.ui.dashboard.home

import com.example.pullcode.base.BasePresenter
import com.example.pullcode.base.BaseView
import com.example.pullcode.response.destinasi.DestinasiResponse
import com.example.pullcode.response.product.ProductResponse

interface HomeContract {
    interface View: BaseView{
        fun homeSuccess(productResponse: ProductResponse)
        fun destinationSuccess(destinasiResponse: DestinasiResponse)
        fun homeFailure(message:String)
        fun destinationFailure(message: String)
    }

    interface Presenter: HomeContract, BasePresenter{
        fun getHome()
        fun getDestinasi()
    }
}