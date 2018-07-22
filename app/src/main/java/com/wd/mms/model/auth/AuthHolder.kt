package com.wd.mms.model.auth

interface AuthHolder {
    var token:String?
    var fcmToken:String?
    var userName: String?
    var fullName: String?
    var endSubscribe: String?
    fun clearData()
}