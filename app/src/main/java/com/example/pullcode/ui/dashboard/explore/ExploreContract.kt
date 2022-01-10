package com.example.pullcode.ui.dashboard.explore

import com.example.pullcode.base.BasePresenter
import com.example.pullcode.base.BaseView
import com.example.pullcode.response.explore.ExploreResponse

interface ExploreContract {
    interface View: BaseView {
        fun exploreSuccess(exploreResponse: ExploreResponse)
        fun exploreFailure(message:String)
    }

    interface Presenter: ExploreContract, BasePresenter {
        fun getExplore()
    }
}