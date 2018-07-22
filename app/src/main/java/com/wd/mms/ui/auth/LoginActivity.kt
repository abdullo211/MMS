package com.wd.mms.ui.auth

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.wd.mms.R
import com.wd.mms.extensions.textChangeListener
import com.wd.mms.extensions.toast
import com.wd.mms.extensions.visible
import com.wd.mms.presentation.auth.LoginPresenter
import com.wd.mms.presentation.auth.LoginView
import com.wd.mms.toothpick.DI
import com.wd.mms.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import toothpick.Toothpick

class LoginActivity : MvpAppCompatActivity(), LoginView {


    @InjectPresenter
    lateinit var presenter: LoginPresenter

    @ProvidePresenter
    fun provide(): LoginPresenter {
        return Toothpick.openScopes(DI.SERVER_SCOPE, DI.LOGIN_SCOPE)
                .getInstance(LoginPresenter::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Toothpick.inject(this, Toothpick.openScopes(DI.APP_SCOPE, DI.SERVER_SCOPE))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initListeners()

    }

    private fun initListeners() {
        signButtonBottom.setOnClickListener {
            presenter.changeSignStateClicked()
        }

        signButton.setOnClickListener {
            presenter.onSignButtonClicked(emailText.text.toString(), fullNameText.text.toString(),
                    passwordText.text.toString(), confirmPasswordText.text.toString())
        }

        forgotText.setOnClickListener {
            presenter.onForgotPasswordClicked(emailText.text.toString())
        }

        fullNameText.textChangeListener {
            if (fullNameLayout.strokeColor == ContextCompat.getColor(this, R.color.colorRed)) {
                fullNameLayout.strokeColor = ContextCompat.getColor(this, R.color.colorGrayLight)
            }
        }
        passwordText.textChangeListener {
            if (passwordLayout.strokeColor == ContextCompat.getColor(this, R.color.colorRed)) {
                passwordLayout.strokeColor = ContextCompat.getColor(this, R.color.colorGrayLight)
            }
        }
        confirmPasswordText.textChangeListener {
            if (confirmPasswordLayout.strokeColor == ContextCompat.getColor(this, R.color.colorRed)) {
                confirmPasswordLayout.strokeColor = ContextCompat.getColor(this, R.color.colorGrayLight)
            }
        }
        emailText.textChangeListener {
            if (emailLayout.strokeColor == ContextCompat.getColor(this, R.color.colorRed)) {
                emailLayout.strokeColor = ContextCompat.getColor(this, R.color.colorGrayLight)
            }
        }
    }

    override fun setState(isSignIn: Boolean) {
        fullNameLayout.visible(!isSignIn)
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
        signButton.visible(!isLoading)
        loginProgress.visible(isLoading)
    }

    override fun showLoginError() {
        fullNameLayout.strokeColor = ContextCompat.getColor(this, R.color.colorRed)
    }

    override fun showPasswordError() {
        passwordLayout.strokeColor = ContextCompat.getColor(this, R.color.colorRed)
    }

    override fun showEmailError() {
        emailLayout.strokeColor = ContextCompat.getColor(this, R.color.colorRed)
    }

    override fun showConfirmError() {
        confirmPasswordLayout.strokeColor = ContextCompat.getColor(this, R.color.colorRed)
    }

    override fun showForgotSendDialog() {
        AlertDialog.Builder(this)
                .setTitle(R.string.title_password)
                .setMessage(R.string.title_forgot_password_send)
                .create()
                .show()
    }

    override fun showError(text: String?) {
            toast(text)
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }

    override fun showMainPage() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
        finish()
    }


}
