package com.wd.mms.model.repository.user

import com.wd.mms.entity.ActiveSubscription
import com.wd.mms.entity.ArrayResponse
import com.wd.mms.entity.Template
import com.wd.mms.entity.User
import com.wd.mms.model.auth.AuthHolder
import com.wd.mms.model.data.api.Api
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import javax.inject.Inject

class UserRepository @Inject constructor(private val api: Api,
                                         private val authHolder: AuthHolder,
                                         private val realm: Realm) {

    fun getUserDetails(): Single<User> = api.getUserDetails()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getSavedUserDetails(): User {
        return User(authHolder.userName, authHolder.fullName)
    }

    fun getActiveSubscription(): Single<ArrayList<ActiveSubscription>> =
            api.getActiveSubscription()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

    fun getTemplates(): Single<ArrayResponse<Template>> =
            api.getTemplates()
                    .doOnSuccess {
                        saveTemplates(it)
                    }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

    private fun saveTemplates(templates: ArrayResponse<Template>) {

        Realm.getDefaultInstance().executeTransaction { realm ->
            realm.insertOrUpdate(templates.list)
        }
    }

    fun getSavedTemplates(): ArrayList<Template> {
        return ArrayList(Realm.getDefaultInstance().where(Template::class.java)
                .findAll())
    }
}