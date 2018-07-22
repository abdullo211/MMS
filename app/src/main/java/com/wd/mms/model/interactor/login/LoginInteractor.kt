package com.wd.mms.model.interactor.login

import com.wd.mms.entity.Device
import com.wd.mms.entity.Token
import com.wd.mms.entity.TokenRequest
import com.wd.mms.model.auth.AuthHolder
import com.wd.mms.model.interactor.fcm.FcmRepository
import com.wd.mms.model.repository.login.LoginRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class LoginInteractor @Inject constructor(private val loginRepository: LoginRepository,
                                          private val fcmRepository: FcmRepository,
                                          private val authHolder: AuthHolder) {

    fun signIn(email: String, password: String, device: Device): Single<Token> =
            loginRepository.signIn(TokenRequest(email = email, password = password, device = device))
            .doAfterSuccess {
                authHolder.token = it.token
                authHolder.endSubscribe = it.subscriptions?.firstOrNull()?.endDate
            }

    fun signUp(email: String, fullName: String, password: String, device: Device): Single<Token> =
            loginRepository.signUp(TokenRequest(email, fullName, password, password, device))
            .doAfterSuccess {
                authHolder.token = it.token
                authHolder.subscriptionTitle = it.subscriptions?.firstOrNull()?.subscription?.title
                authHolder.endSubscribe = it.subscriptions?.firstOrNull()?.endDate
            }

    fun fcmToken(): String? = fcmRepository.getToken()

    fun forgotPassword(username: String): Completable = loginRepository.forgotPassword(username)



}