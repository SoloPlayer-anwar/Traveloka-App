package com.example.pullcode.ui.login

import com.example.pullcode.base.BasePresenter
import com.example.pullcode.base.BaseView
import com.example.pullcode.response.sign.SignResponse

interface LoginContract {
    interface View: BaseView {
        fun loginSuccess(signResponse: SignResponse)
        fun loginFailure(message:String)
    }

    interface Presenter: LoginContract, BasePresenter {
        fun submitLogin(email:String, password:String)
    }
}