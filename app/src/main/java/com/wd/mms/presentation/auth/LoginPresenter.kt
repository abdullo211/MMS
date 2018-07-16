package com.wd.mms.presentation.auth

import com.arellomobile.mvp.InjectViewState
import com.wd.mms.R
import com.wd.mms.extensions.isValidEmail
import com.wd.mms.model.interactor.LoginInteractor
import com.wd.mms.model.system.ResourceManager
import com.wd.mms.presentation.base.BasePresenter
import retrofit2.HttpException
import javax.inject.Inject

@InjectViewState
class LoginPresenter @Inject constructor(private val loginInteractor: LoginInteractor,
                                         private val resourceManager: ResourceManager) : BasePresenter<LoginView>() {

    private var isSignIn = true

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setState(isSignIn)
    }

    fun changeSignStateClicked() {
        isSignIn = !isSignIn
        viewState.setState(isSignIn)
    }

    fun onSignButtonClicked(email: String, login: String, password: String, confirm: String) {
        if (validate(email, login, password, confirm))
            if (isSignIn)
                signIn(login, password)
            else
                signUp(email, login, password)
    }

    private fun signIn(login: String, password: String) {
        loginInteractor.signIn(login, password)
                .doOnSubscribe { viewState.showProgress(true) }
                .doAfterTerminate { viewState.showProgress(false) }
                .subscribe({
                    viewState.showMainPage()
                }, {
                    if (it is HttpException)
                        viewState.showError(resourceManager.getString(R.string.message_login_or_password_incorrect))
                }).apply {
                    connect()
                }
    }

    private fun signUp(email: String, login: String, password: String) {
        loginInteractor.signUp(email, login, password)
                .doOnSubscribe { viewState.showProgress(true) }
                .doAfterTerminate { viewState.showProgress(false) }
                .subscribe({
                    viewState.showMainPage()
                }, {
                    if (it is HttpException)
                        viewState.showError(resourceManager.getString(R.string.message_login_or_password_incorrect))
                }).apply {
                    connect()
                }
    }

    private fun validate(email: String, login: String, password: String, confirm: String): Boolean {
        var validated = true
        if (login.isBlank()) {
            validated = false
            viewState.showLoginError()
        }
        if (password.isBlank()) {
            validated = false
            viewState.showPasswordError()
        }
        if (!isSignIn) {
            if (!email.isValidEmail()) {
                validated = false
                viewState.showEmailError()
            }
            if (password != confirm) {
                validated = false
                viewState.showConfirmError()
            }
        }
        return validated
    }


}