package com.wd.mms.presentation.auth

import com.arellomobile.mvp.InjectViewState
import com.wd.mms.R
import com.wd.mms.entity.Device
import com.wd.mms.extensions.isValidEmail
import com.wd.mms.model.interactor.login.LoginInteractor
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

    fun onSignButtonClicked(email: String, fullName: String, password: String, confirm: String) {
        if (validate(email, fullName, password, confirm)) {
            val sign = if (isSignIn)
                loginInteractor.signIn(email, password, Device(fcmToken = loginInteractor.fcmToken()))
            else
                loginInteractor.signUp(email, fullName, password, Device(fcmToken = loginInteractor.fcmToken()))

            sign
                    .doOnSubscribe { viewState.showProgress(true) }
                    .doAfterTerminate { viewState.showProgress(false) }
                    .subscribe({
                        viewState.showMainPage()
                    }, {
                        if (it is HttpException)
                            viewState.showError(it.message)
                    }).apply {
                        connect()
                    }
        }
    }


    fun onForgotPasswordClicked(username: String) {
        if (!username.isValidEmail())
            viewState.showEmailError()
        else
            loginInteractor.forgotPassword(username)
                    .doOnSubscribe { viewState.showProgress(true) }
                    .doOnTerminate { viewState.showProgress(false) }
                    .subscribe({
                        viewState.showForgotSendDialog()
                    }, {
                        viewState.showError(resourceManager.getString(R.string.error_operation))
                    })
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