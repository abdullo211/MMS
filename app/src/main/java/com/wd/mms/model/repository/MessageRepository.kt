package com.wd.mms.model.repository

import com.wd.mms.model.data.Api
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MessageRepository @Inject constructor(private val api: Api) {

    fun getMessages(page: Int) = api.getMessages(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())


}