package com.wd.mms.ui.auth

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.wd.mms.R
import com.wd.mms.extensions.toast
import com.wd.mms.extensions.visible
import com.wd.mms.presentation.auth.LoginPresenter
import com.wd.mms.presentation.auth.LoginView
import com.wd.mms.toothpick.DI
import kotlinx.android.synthetic.main.activity_login.*
import toothpick.Toothpick

class LoginActivity : MvpAppCompatActivity(), LoginView {



    @InjectPresenter
    lateinit var presenter: LoginPresenter

    @ProvidePresenter
    fun provide(): LoginPresenter {
        return Toothpick.openScopes(DI.SERVER_SCOPE)
                .getInstance(LoginPresenter::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Toothpick.inject(this, Toothpick.openScopes(DI.APP_SCOPE))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signButtonBottom.setOnClickListener {
            presenter.changeSignStateClicked()
        }

        signButton.setOnClickListener {
            presenter.onSignButtonClicked(emailText.text.toString(), userNameText.text.toString(),
                    passwordText.text.toString(), confirmPasswordText.text.toString())
        }
    }

    override fun setState(isSignIn: Boolean) {
        emailLayout.visible(!isSignIn)
        confirmPasswordLayout.visible(!isSignIn)
        signButton.setText(if (isSignIn) R.string.title_sign_in else R.string.title_sign_up)
        signButtonBottom.setText(if (!isSignIn) R.string.title_sign_in else R.string.title_sign_up)
        forgotText.visible(isSignIn)
    }

    override fun onDestroy() {
        if (isFinishing)
            Toothpick.closeScope(DI.LOGIN_SCOPE)
        super.onDestroy()
    }

    override fun showProgress(isLoading: Boolean) {
        signButtonBottom.visible(!isLoading)
        loginProgress.visible(isLoading)
    }

    override fun showLoginError() {

    }

    override fun showPasswordError() {

    }

    override fun showEmailError() {

    }

    override fun showConfirmError() {

    }

    override fun showError(text: String?) {
            toast(text)
    }

    override fun showMainPage() {

    }

}
