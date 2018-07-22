package com.wd.mms.presentation.template

import com.arellomobile.mvp.MvpView

interface TemplateView :MvpView {

    fun setBody(htmlBody:String)
}