package com.wd.mms.presentation.messages.list

import com.arellomobile.mvp.InjectViewState
import com.wd.mms.model.interactor.MessageInteractor
import com.wd.mms.presentation.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class MessageListPresenter @Inject constructor(private val messageInteractor: MessageInteractor)
    : BasePresenter<MessageListView>() {
    private var currentPage = 1

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getMessages()
    }

    private fun getMessages() {
        messageInteractor.getMessages(currentPage)
                .doOnSubscribe { viewState.showProgress(true) }
                .doAfterTerminate { viewState.showProgress(false) }
                .subscribe({
                    viewState.addMessages(it.list)
                    currentPage++
                }, {})
                .apply { connect() }
    }

    fun onMessageShowMoreClicked(messageId: Int) {

    }

    fun OnLoadMore() {

    }

    fun onReloadClicked() {
        currentPage = 1
        viewState.clearMessages()
        getMessages()
    }
}