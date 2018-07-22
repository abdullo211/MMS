package com.wd.mms.presentation.messages.list

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.wd.mms.entity.Message
import com.wd.mms.entity.Subscription

interface MessageListView : MvpView {

    @StateStrategyType(AddToEndStrategy::class)
    fun addMessages(messages:ArrayList<Message>)

    fun showProgress(isShown: Boolean)

    fun noMoreLoad()

    fun clearMessages()

    fun showMainProgress(isLoading: Boolean)

    fun showMessageDetails(messageId: Int)

    fun showSubscribeDialog(subscriptions: ArrayList<Subscription>)

    fun showPaymentPage(subscription: Long)
}