package com.wd.mms.model.repository.login

import com.wd.mms.entity.Token
import com.wd.mms.model.data.Api
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginRepository @Inject constructor(private val api: Api) {

    fun signIn(email: String, password: String): Single<Token> = api.signIn(email, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun signUp(username:String,fullName:String,password:String): Single<Token> =
            api.signUp(username,fullName,password,password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

}