package com.example.pullcode.ui.dashboard.profile

import android.net.Uri
import com.example.pullcode.base.BasePresenter
import com.example.pullcode.base.BaseView
import com.example.pullcode.response.sign.SignResponse

interface ProfileContract {
    interface View: BaseView {
        fun photoProfileSuccess(signResponse: SignResponse)
        fun photoFailure(message:String)
    }

    interface Presenter: ProfileContract, BasePresenter {
        fun submitPhotoProfile(filePath: Uri)
    }
}