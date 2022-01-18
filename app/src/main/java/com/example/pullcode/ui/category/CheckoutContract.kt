package com.example.pullcode.ui.category

import com.example.pullcode.base.BasePresenter
import com.example.pullcode.base.BaseView
import com.example.pullcode.response.checkout.CheckoutResponse

interface CheckoutContract {
    interface View: BaseView {
        fun checkoutDestination(checkoutResponse: CheckoutResponse)
        fun checkoutFailure(message:String)
    }

    interface Presenter: CheckoutContract, BasePresenter {
        fun submitDestination(userId: Int,
                              quantity:Int,
                              total: Int,
                              status:String,
                              destinationId: Int,
                              checkin:String,
                              namaBandara:String,
                              provinsi:String,
                              jamTerbang:String,
                              picturePesawat:String,
                              pictureProduct:String,
                              name:String,
                              rating:Double)
    }
}