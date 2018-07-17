package com.wd.mms.model.auth

interface AuthHolder {
    var token:String?
    var userName: String?
    var fullName: String?
    fun clearData()
}