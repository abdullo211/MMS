package com.wd.mms.presentation.main

import com.arellomobile.mvp.InjectViewState
import com.wd.mms.R
import com.wd.mms.model.interactor.main.MainInteractor
import com.wd.mms.model.system.ResourceManager
import com.wd.mms.presentation.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(private val mainInteractor: MainInteractor,
                                        private val resourceManager: ResourceManager) : BasePresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        if (mainInteractor.isTokenEmpty())
            viewState.showLoginPage()
        else
            onMessagesMenuClicked()

        if (mainInteractor.isUserDetailsEmpty())
            getUserDetails()
        else
            viewState.addTemplates(mainInteractor.getSavedTemplates())

    }

    private fun getUserDetails() {
        mainInteractor.getUserDetails().flatMap { _ ->
            return@flatMap mainInteractor.getTemplates()
        }
                .doOnSubscribe { viewState.showProgress(true) }
                .doFinally { viewState.showProgress(false) }
                .subscribe({
                    viewState.addTemplates(it.list)
                }, {})
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

    fun onTemplateMenuClick(itemId: Int) {
        val template = mainInteractor.getSavedTemplates().find { it.id == itemId }
        template?.body?.let {
            viewState.showTemplatePage(it)
        }
        template?.title.let {
            viewState.changeToolbarTitle(it)
        }
    }

    fun onMessagesMenuClicked() {
        viewState.showMessagesPage()
        viewState.changeToolbarTitle(resourceManager.getString(R.string.title_messages))
    }
}