package com.wd.mms.presentation.messages.info

import com.arellomobile.mvp.InjectViewState
import com.wd.mms.model.interactor.message.MessageInteractor
import com.wd.mms.presentation.base.BasePresenter
import com.wd.mms.toothpick.PrimitiveWrapper
import com.wd.mms.toothpick.qualifier.MessageId
import javax.inject.Inject

@InjectViewState
class MessageInfoPresenter @Inject constructor(private val messageInteractor: MessageInteractor,
                                               @MessageId private val messageWrapper: PrimitiveWrapper<Int?>)
    : BasePresenter<MessageInfoView>() {


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        messageWrapper.value?.let {
            messageInteractor.getMessage(it)
                    .doOnSubscribe { viewState.showProgress(true) }
                    .doAfterTerminate { viewState.showProgress(false) }
                    .subscribe({ message ->
                        viewState.showMessageDetails(message)
                    }, {

                    })
                    .apply { connect() }
        }

    }
}