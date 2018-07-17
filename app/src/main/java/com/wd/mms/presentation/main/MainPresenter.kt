package com.wd.mms.presentation.main

import com.arellomobile.mvp.InjectViewState
import com.wd.mms.model.interactor.main.MainInteractor
import com.wd.mms.presentation.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(private val mainInteractor: MainInteractor) : BasePresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        if (mainInteractor.isTokenEmpty())
            viewState.showLoginPage()
        else
            viewState.showMessagesPage()

        if (mainInteractor.isUserDetailsEmpty())
            getUserDetails()

    }

    private fun getUserDetails() {
        mainInteractor.getUserDetails()
                .subscribe({},{})
                .connect()
    }

    fun onActivityResult() {
        if (mainInteractor.isTokenEmpty())
            viewState.close()
        else
            viewState.showMessagesPage()
    }
    fun onUserClicked(){

    }

    fun onLogOutMenuClicked() {
        viewState.showLogOutDialog()
    }

    fun logOutClicked() {
        mainInteractor.clearAllUserData()
        viewState.showLoginPage()
    }
}