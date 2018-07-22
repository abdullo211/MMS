package com.wd.mms.presentation.main

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.wd.mms.entity.Template

interface MainView : MvpView {

    fun showMessagesPage()

    fun showLoginPage()

    fun close()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showLogOutDialog()

    fun showProgress(isLoading: Boolean)

    fun addTemplates(templates: ArrayList<Template>)

    fun showTemplatePage(body: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun changeToolbarTitle(it: String?)

    fun showUserMenu()

}