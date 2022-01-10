package com.example.pullcode.ui.register

import com.example.pullcode.base.BasePresenter
import com.example.pullcode.base.BaseView
import com.example.pullcode.response.sign.SignResponse

interface RegisterContract {
    interface View:BaseView {
        fun registerSuccess(signResponse: SignResponse)
        fun registerFailure(message:String)
    }

    interface Presenter: RegisterContract, BasePresenter {
        fun submitRegister(name:String,
                           email:String,
                           password:String,
                           passwordConfirm:String,
                           address:String,
                           phone:String,
                           city:String)
    }
}