package com.wd.mms.ui.messages.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.wd.mms.R
import com.wd.mms.entity.Message
import com.wd.mms.presentation.messages.list.MessageListPresenter
import com.wd.mms.presentation.messages.list.MessageListView
import com.wd.mms.toothpick.DI
import kotlinx.android.synthetic.main.fragment_messages_list.*
import toothpick.Toothpick

class MessagesListFragment : MvpAppCompatFragment(), MessageListView {

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

    override fun noMoreLoad() {
        messagesPlaceHolder.noMoreToLoad()
    }

    override fun clearMessages() {
        messagesPlaceHolder.removeAllViews()
        messagesPlaceHolder.setLoadMoreResolver(LoadMoreIListItem { presenter.onLoadMore() })
    }

}