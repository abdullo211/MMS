package com.wd.mms.model.repository.user

import com.wd.mms.entity.User
import com.wd.mms.model.data.Api
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UserRepository @Inject constructor(private val api: Api) {

    fun getUserDetails(): Single<User> = api.getUserDetails()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}