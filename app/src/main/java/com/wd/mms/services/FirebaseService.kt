package com.wd.mms.services

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.wd.mms.model.interactor.fcm.FcmRepository
import com.wd.mms.toothpick.DI
import toothpick.Toothpick
import javax.inject.Inject

class FirebaseService : FirebaseMessagingService() {

    @Inject
    lateinit var fcmRepository: FcmRepository

    override fun onCreate() {
        Toothpick.inject(this,Toothpick.openScopes(DI.APP_SCOPE,DI.SERVER_SCOPE))
        super.onCreate()
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener { instanceIdResult ->
            fcmRepository.updateToken(instanceIdResult.token)
        }
    }

    override fun onMessageReceived(p0: RemoteMessage?) {
        super.onMessageReceived(p0)
    }



    override fun onNewToken(token: String?){
        token?.let {
            fcmRepository.updateToken(token)
        }
        super.onNewToken(token)
    }


}