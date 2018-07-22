package com.wd.mms.presentation.user

import com.arellomobile.mvp.MvpView
import com.wd.mms.entity.ActiveSubscription
import com.wd.mms.entity.Subscription

interface UserView : MvpView {
    fun setUserName(username: String?)
    fun setUserFullName(fullname: String?)
    fun showNoSubscriptionText()
    fun showSubscription(subscription: ActiveSubscription)

}