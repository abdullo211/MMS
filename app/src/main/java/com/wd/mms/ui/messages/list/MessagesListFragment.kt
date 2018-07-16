package com.wd.mms.ui.messages.list

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.wd.mms.R
import com.wd.mms.entity.Message
import com.wd.mms.presentation.messages.list.MessageListPresenter
import com.wd.mms.presentation.messages.list.MessageListView
import kotlinx.android.synthetic.main.fragment_messages_list.*
import com.wd.mms.extensions.visible

class MessagesListFragment : Fragment(), MessageListView {

    @InjectPresenter
    lateinit var presenter: MessageListPresenter

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_messages_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    //    messagesPlaceHolder.setLoadMoreResolver(LoadMoreIListItem { presenter.OnLoadMore() })
    }

    override fun addMessages(messages: ArrayList<Message>) {
        messages.forEach {
         //   messagesPlaceHolder.addView(MessageListItem(it) { id -> presenter.onMessageShowMoreClicked(id) })
        }
    }

    override fun showProgress(isShown: Boolean) {
       /* if (messagesPlaceHolder.allViewResolvers.isNotEmpty())
            messagesProgress.visible(isShown)
        else
            if (!isShown)
                messagesPlaceHolder.loadingDone()
                */
    }

    override fun noMoreLoad() {
      //  messagesPlaceHolder.noMoreToLoad()
    }

    override fun clearMessages() {
      //  messagesPlaceHolder.removeAllViews()
    }

}