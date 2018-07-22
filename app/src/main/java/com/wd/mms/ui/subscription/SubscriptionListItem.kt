package com.wd.mms.ui.subscription

import android.support.v7.widget.AppCompatTextView
import com.mindorks.placeholderview.annotations.Click
import com.mindorks.placeholderview.annotations.Layout
import com.mindorks.placeholderview.annotations.Resolve
import com.mindorks.placeholderview.annotations.View
import com.wd.mms.R
import com.wd.mms.entity.Subscription
import java.text.DecimalFormat

@Layout(R.layout.list_item_subscription)
class SubscriptionListItem(private val subscription: Subscription,
                           private val onSubscriptionSelect: (Subscription) -> Unit) {

    @View(R.id.subscription_title)
    lateinit var title: AppCompatTextView

    @View(R.id.subscription_amount)
    lateinit var amount: AppCompatTextView


    @Resolve
    fun onResolve() {
        title.text = subscription.title
        amount.text = "Price: " + DecimalFormat("###.##").format(subscription.amount) + " $"
    }

    @Click(R.id.subscriptionMainLayout)
    fun onSubscriptionClick() {
        onSubscriptionSelect.invoke(subscription)
    }

}