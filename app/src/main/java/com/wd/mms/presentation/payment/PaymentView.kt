package com.wd.mms.presentation.payment

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface PaymentView : MvpView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showCardNumberError()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showExpMonthError()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showExpYearError()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showCVCError()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showProgress(isLoading: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showError(s: String)

    fun showSuccessMessage()
}