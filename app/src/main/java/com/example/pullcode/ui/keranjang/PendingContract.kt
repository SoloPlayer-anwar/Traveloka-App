package com.example.pullcode.ui.keranjang

import com.example.pullcode.base.BasePresenter
import com.example.pullcode.base.BaseView
import com.example.pullcode.response.transaction.TransactionResponse

interface PendingContract {
    interface View: BaseView {
        fun transactionPending(transactionResponse: TransactionResponse)
        fun transactionFailure(message:String)
    }

    interface Presenter: PendingContract, BasePresenter {
        fun submitPending(status:String)
    }
}