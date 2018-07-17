package com.wd.mms.model.interactor

import com.wd.mms.entity.Token
import com.wd.mms.model.auth.AuthHolder
import com.wd.mms.model.repository.login.LoginRepository
import io.reactivex.Single
import javax.inject.Inject

class LoginInteractor @Inject constructor(private val loginRepository: LoginRepository,
                                          private val authHolder: AuthHolder) {

    fun signIn(email: String, password: String):
            Single<Token> = loginRepository.signIn(email, password)
            .doAfterSuccess {
                saveToken(it)
            }


    fun signUp(email: String, fullName: String, password: String):
            Single<Token> = loginRepository.signUp(email, fullName, password)
            .doAfterSuccess {
                saveToken(it)
            }


    private fun saveToken(token: Token) {
        authHolder.token = token.token
    }
}