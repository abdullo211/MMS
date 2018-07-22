package com.wd.mms.model.repository.user

import com.wd.mms.entity.ArrayResponse
import com.wd.mms.entity.Template
import com.wd.mms.entity.User
import com.wd.mms.model.data.api.Api
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import javax.inject.Inject

class UserRepository @Inject constructor(private val api: Api,
                                         private val realm: Realm) {

    fun getUserDetails(): Single<User> = api.getUserDetails()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getTemplates(): Single<ArrayResponse<Template>> =
            api.getTemplates()
                    .doOnSuccess { saveTemplates(it) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

    private fun saveTemplates(templates: ArrayResponse<Template>) {
        realm.executeTransaction { realm ->
            realm.insertOrUpdate(templates.list)
        }
    }

    fun getSavedTemplates(): ArrayList<Template> {
        return ArrayList(realm.where(Template::class.java)
                .findAll())
    }
}