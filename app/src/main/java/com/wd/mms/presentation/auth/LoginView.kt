package com.wd.mms.presentation.auth

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface LoginView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setState(isSignIn: Boolean)

    fun showProgress(isLoading: Boolean)

    fun showLoginError()

    fun showPasswordError()

    fun showEmailError()

    fun showConfirmError()

    fun showError(text: String?)

    fun showMainPage()
}