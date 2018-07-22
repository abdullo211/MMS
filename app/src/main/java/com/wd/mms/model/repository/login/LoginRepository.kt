package com.wd.mms.model.repository.login

import com.wd.mms.entity.ArrayResponse
import com.wd.mms.entity.Template
import com.wd.mms.entity.Token
import com.wd.mms.entity.TokenRequest
import com.wd.mms.model.data.api.Api
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import javax.inject.Inject

class LoginRepository @Inject constructor(private val api: Api) {

    fun signIn(tokenRequest: TokenRequest): Single<Token> = api.signIn(tokenRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun signUp(tokenRequest: TokenRequest): Single<Token> =
            api.signUp(tokenRequest)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

    fun forgotPassword(username: String): Completable =
            api.forgotPassword(username)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())




}