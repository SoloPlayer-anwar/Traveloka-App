package com.example.pullcode.airport

import com.example.pullcode.base.BasePresenter
import com.example.pullcode.base.BaseView
import com.example.pullcode.response.bandara.BandaraResponse

interface BandaraContract {
    interface View: BaseView {
        fun airportSuccess(bandaraResponse: BandaraResponse)
        fun airportFailure(message:String)
    }

    interface Presenter: BandaraContract, BasePresenter{
        fun submitAirport()
    }
}