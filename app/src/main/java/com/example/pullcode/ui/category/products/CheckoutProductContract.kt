package com.example.pullcode.ui.category.products

import com.example.pullcode.base.BasePresenter
import com.example.pullcode.base.BaseView
import com.example.pullcode.response.checkout.CheckoutResponse
import com.example.pullcode.ui.category.CheckoutContract

interface CheckoutProductContract {
    interface View: BaseView {
        fun checkoutProduct(checkoutResponse: CheckoutResponse)
        fun productFailure(message:String)
    }

    interface Presenter: CheckoutContract, BasePresenter {
            fun submitProduct(userId: Int,
                              quantity:Int,
                              total: Int,
                              status:String,
                              productId: Int,
                              checkin:String,
                              pictureProduct:String,
                              name:String,
                              rating:Double)
    }
}