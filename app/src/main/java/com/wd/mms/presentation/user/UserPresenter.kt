package com.wd.mms.presentation.user

import com.arellomobile.mvp.InjectViewState
import com.wd.mms.model.interactor.user.UserInteractor
import com.wd.mms.presentation.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class UserPresenter @Inject constructor(private val userInteractor: UserInteractor) : BasePresenter<UserView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        userInteractor.getUserDetails().apply {
            viewState.setUserName(username)
            viewState.setUserFullName(fullname)
        }

        userInteractor.getUserSubscription()
                .subscribe({
                    viewState.showSubscription(it.first())
                }, {
                    it.printStackTrace()
                    viewState.showNoSubscriptionText()
                }).apply {
                    connect()
                }
    }
}