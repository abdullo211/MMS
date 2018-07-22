package com.wd.mms.ui.messages.list

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.wd.mms.R
import com.wd.mms.entity.Message
import com.wd.mms.entity.Subscription
import com.wd.mms.presentation.messages.list.MessageListPresenter
import com.wd.mms.presentation.messages.list.MessageListView
import com.wd.mms.toothpick.DI
import com.wd.mms.ui.common.BaseFragment
import com.wd.mms.ui.messages.view.MessageActivity
import com.wd.mms.ui.payment.PaymentActivity
import com.wd.mms.ui.subscription.SubscriptionDialog
import kotlinx.android.synthetic.main.fragment_messages_list.*
import toothpick.Toothpick

class MessagesListFragment : BaseFragment(), MessageListView,
        SubscriptionDialog.SubscriptionSelectedListener {


    @InjectPresenter
    internal lateinit var presenter: MessageListPresenter

    @ProvidePresenter
    fun provide(): MessageListPresenter {
        val scope = "messages${hashCode()}"
        return Toothpick.openScopes(DI.SERVER_SCOPE, scope)
                .getInstance(MessageListPresenter::class.java)
                .apply {
                    Toothpick.closeScope(this)
                }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_messages_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        messagesPlaceHolder.setLoadMoreResolver(LoadMoreIListItem { presenter.onLoadMore() })
        messagesSwipeRefresh.setOnRefreshListener {
            presenter.onReloadClicked()
            messagesSwipeRefresh.isRefreshing = false
        }
    }

    override fun addMessages(messages: ArrayList<Message>) {
        messages.forEach {
            messagesPlaceHolder.addView(
                    MessageListItem(it)
                    { id -> presenter.onMessageShowMoreClicked(id) })
        }
    }

    override fun showProgress(isShown: Boolean) {
            if (!isShown)
                messagesPlaceHolder.loadingDone()
    }

    override fun showMainProgress(isLoading: Boolean) {
        // showProgressDialog(isLoading)
    }

    override fun noMoreLoad() {
        messagesPlaceHolder.noMoreToLoad()
    }

    override fun clearMessages() {
        messagesPlaceHolder.removeAllViews()
        messagesPlaceHolder.setLoadMoreResolver(LoadMoreIListItem { presenter.onLoadMore() })
    }

    override fun showMessageDetails(messageId: Int) {
        val intent = Intent(context, MessageActivity::class.java).apply {
            putExtra("message", messageId)
        }
        startActivity(intent)
    }

    override fun showSubscribeDialog(subscriptions: ArrayList<Subscription>) {
        val bundle = Bundle().apply {
            putParcelableArrayList("subscriptions", subscriptions)
        }
        SubscriptionDialog().apply { arguments = bundle }.show(childFragmentManager, "subscription")
    }

    override fun onSubscriptionSelected(subscription: Subscription) {
        presenter.onSubscriptionSelected(subscription)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PAYMENT_RESULT && resultCode == RESULT_OK)
            presenter.onPaymentSuccess()
    }

    override fun showPaymentPage(subscriptionId: Long) {
        val intent = Intent(context, PaymentActivity::class.java)
        intent.putExtra(Subscription::id.name, subscriptionId)
        startActivityForResult(intent, PAYMENT_RESULT)
    }

    companion object {
        const val PAYMENT_RESULT = 707
    }
}