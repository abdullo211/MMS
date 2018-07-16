package com.wd.mms.model.interactor

import com.wd.mms.entity.Token
import com.wd.mms.model.auth.AuthHolder
import com.wd.mms.model.repository.login.LoginRepository
import io.reactivex.Single
import javax.inject.Inject

class LoginInteractor @Inject constructor(private val loginRepository: LoginRepository,
                                          private val authHolder: AuthHolder) {

    fun signIn(login: String, password: String):
            Single<Token> = loginRepository.signIn(login, password)
            .doOnSuccess { authHolder.token = it.token }

    fun signUp(email: String, fullName: String, password: String):
            Single<Token> = loginRepository.signUp(email, fullName, password)
            .doOnSuccess { authHolder.token = it.token }
}