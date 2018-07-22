package com.wd.mms.model.storage

import android.content.Context
import android.content.SharedPreferences
import com.wd.mms.model.auth.AuthHolder
import javax.inject.Inject

class AuthPrefs @Inject constructor(private val context: Context) : AuthHolder {

    companion object {
        private const val APP_DATA = "app_data"
        private const val AUTH_DATA = "auth_data"
        private const val TOKEN = "pref_token"
        private const val USERNAME = "username"
        private const val FCM_TOKEN = "fcm_token"
        private const val FULL_NAME = "fullName"
        private const val SUBSCRIBE_TITLE= "subscribe_title"
        private const val SUBSCRIBE_DATE = "subscribe_end_date"
    }

    override var token: String?
        get() = getSharedPreferences(AUTH_DATA).getString(TOKEN, null)
        set(value) {
            getSharedPreferences(AUTH_DATA).edit().putString(TOKEN, "Token $value").apply()
        }

    private fun getSharedPreferences(name: String): SharedPreferences {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

    override var userName: String?
        get() = getSharedPreferences(AUTH_DATA).getString(USERNAME, null)
        set(value) {
            getSharedPreferences(AUTH_DATA).edit().putString(USERNAME, value).apply()
        }

    override var fullName: String?
        get() = getSharedPreferences(AUTH_DATA).getString(FULL_NAME, null)
        set(value) {
            getSharedPreferences(AUTH_DATA).edit().putString(FULL_NAME, value).apply()
        }

    override var endSubscribe: String?
        get() = getSharedPreferences(AUTH_DATA).getString(SUBSCRIBE_DATE, null)
        set(value) {
            getSharedPreferences(AUTH_DATA).edit().putString(SUBSCRIBE_DATE, value).apply()
        }

    override var fcmToken: String?
        get() = getSharedPreferences(APP_DATA).getString(FCM_TOKEN, null)
        set(value) {
            getSharedPreferences(APP_DATA).edit().putString(FCM_TOKEN, value).apply()
        }

    override var subscriptionTitle: String?
        get() = getSharedPreferences(AUTH_DATA).getString(SUBSCRIBE_TITLE, null)
        set(value) {
            getSharedPreferences(AUTH_DATA).edit().putString(SUBSCRIBE_TITLE, value).apply()
        }

    override fun clearData() {
        getSharedPreferences(AUTH_DATA).edit().clear().apply()
    }
}