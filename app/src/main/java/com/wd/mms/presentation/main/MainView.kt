package com.wd.mms.presentation.main

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface MainView : MvpView {

    fun showMessagesPage()

    fun showLoginPage()

    fun close()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showLogOutDialog()
}