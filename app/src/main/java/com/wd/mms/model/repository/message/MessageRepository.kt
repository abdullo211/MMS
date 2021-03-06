package com.wd.mms.model.repository.message

import com.wd.mms.entity.ArrayResponse
import com.wd.mms.entity.Message
import com.wd.mms.entity.Subscription
import com.wd.mms.model.data.api.Api
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MessageRepository @Inject constructor(private val api: Api) {

    fun getMessages(page: Int): Single<ArrayResponse<Message>> =
            api.getMessages(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())


    fun getMessage(messageId: Int): Single<Message> =
            api.getMessage(messageId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

}