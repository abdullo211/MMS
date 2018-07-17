package com.wd.mms.presentation.auth

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.wd.mms.R
import com.wd.mms.extensions.isValidEmail
import com.wd.mms.model.interactor.LoginInteractor
import com.wd.mms.model.system.ResourceManager
import com.wd.mms.presentation.base.BasePresenter
import com.wd.mms.toothpick.DI
import retrofit2.HttpException
import toothpick.Toothpick
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

    fun onSignButtonClicked(email: String, fullName: String, password: String, confirm: String) {
        if (validate(email, fullName, password, confirm))
            if (isSignIn)
                signIn(email, password)
            else
                signUp(email, fullName, password)
    }

    private fun signIn(email: String, password: String) {
        loginInteractor.signIn(email, password)
                .doOnSubscribe { viewState.showProgress(true) }
                .doAfterTerminate { viewState.showProgress(false) }
                .subscribe({
                    openCloseScope()
                    viewState.showMainPage()
                }, {
                    if (it is HttpException)
                        viewState.showError(resourceManager.getString(R.string.message_login_or_password_incorrect))
                }).apply {
                    connect()
                }
    }

    private fun openCloseScope() {
        Toothpick.closeScope(DI.SERVER_SCOPE)
        Toothpick.openScopes(DI.APP_SCOPE, DI.SERVER_SCOPE)
    }

    private fun signUp(email: String, fullName: String, password: String) {
        loginInteractor.signUp(email, fullName, password)
                .doOnSubscribe {
                    viewState.showProgress(true)
                }
                .doAfterTerminate {
                    viewState.showProgress(false)
                }
                .subscribe({
                    viewState.showMainPage()
                }, {
                    if (it is HttpException)
                        viewState.showError(it.message)
                }).apply {
                    connect()
                }
    }


    private fun validate(email: String, fullName: String, password: String, confirm: String): Boolean {
        var validated = true
        if (!email.isValidEmail()) {
            validated = false
            viewState.showEmailError()
        }
        if (password.isBlank()) {
            validated = false
            viewState.showPasswordError()
        }
        if (!isSignIn) {
            if (fullName.isBlank()) {
                validated = false
                viewState.showLoginError()
            }
            if (password != confirm || confirm.isBlank()) {
                validated = false
                viewState.showConfirmError()
            }
        }
        return validated
    }


}