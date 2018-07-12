package com.wd.mms.presentation.messages.list

import com.arellomobile.mvp.MvpView
import com.wd.mms.entity.Message

interface MessageListView : MvpView {

    fun addMessages(messages:ArrayList<Message>)

    fun showProgress(isShown: Boolean)

    fun noMoreLoad()

    fun clearMessages()


}