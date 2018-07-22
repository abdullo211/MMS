package com.wd.mms.ui.subscription

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wd.mms.R
import com.wd.mms.entity.Subscription
import kotlinx.android.synthetic.main.dialog_subscription.*

class SubscriptionDialog : DialogFragment() {

    interface SubscriptionSelectedListener {
        fun onSubscriptionSelected(subscription: Subscription)
    }

    private val subscriptions: ArrayList<Subscription>?
        get() = arguments?.getParcelableArrayList<Subscription>("subscriptions")

    private var subscriptionListener: SubscriptionSelectedListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_subscription, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (parentFragment is SubscriptionSelectedListener)
            subscriptionListener = parentFragment as SubscriptionSelectedListener

        subscriptions?.forEach {
            subscriptionsPlaceHolder.addView(SubscriptionListItem(it) { subscriptionListener?.onSubscriptionSelected(it) })
        }
    }
}