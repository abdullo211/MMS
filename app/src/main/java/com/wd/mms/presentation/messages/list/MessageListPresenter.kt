package com.wd.mms.presentation.messages.list

import com.arellomobile.mvp.InjectViewState
import com.wd.mms.model.interactor.MessageInteractor
import com.wd.mms.presentation.base.BasePresenter
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@InjectViewState
class MessageListPresenter @Inject constructor(private val messageInteractor: MessageInteractor)
    : BasePresenter<MessageListView>() {
    private var currentPage = 1
    private var isLastPage = false
    private var messageDisposable: Disposable? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getMessages()
    }

    private fun getMessages() {
        messageDisposable?.dispose()
        messageDisposable = messageInteractor.getMessages(currentPage)
                .doOnSubscribe { viewState.showProgress(true) }
                .doAfterTerminate { viewState.showProgress(false) }
                .subscribe({
                    isLastPage = it.nextPage == null
                    currentPage++
                    viewState.addMessages(it.list)
                    if (isLastPage)
                        viewState.noMoreLoad()
                }, {
                    it.printStackTrace()
                })
                .apply { connect() }
    }

    fun onMessageShowMoreClicked(messageId: Int) {

    }

    fun onLoadMore() {
        if (messageDisposable?.isDisposed == true && !isLastPage)
            getMessages()
    }

    fun onReloadClicked() {
        currentPage = 1
        viewState.clearMessages()
        getMessages()
    }
}