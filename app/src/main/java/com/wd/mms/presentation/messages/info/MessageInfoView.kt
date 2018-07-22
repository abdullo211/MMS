package com.wd.mms.presentation.messages.info

import com.arellomobile.mvp.MvpView
import com.wd.mms.entity.Message

interface MessageInfoView : MvpView {

    fun showMessageDetails(message: Message)

    fun showProgress(isLoading: Boolean)
}