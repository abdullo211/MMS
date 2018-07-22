package com.wd.mms.model.interactor.fcm

import com.wd.mms.model.auth.AuthHolder
import com.wd.mms.model.data.api.Api
import javax.inject.Inject

class FcmRepository @Inject constructor(val authHolder: AuthHolder,
                                        api: Api) {

    fun getToken(): String? = authHolder.fcmToken

    fun updateToken(token: String?) {
        authHolder.fcmToken = token
        sendTokenToServer(token)
    }

    fun sendTokenToServer(token:String?){
    }

}