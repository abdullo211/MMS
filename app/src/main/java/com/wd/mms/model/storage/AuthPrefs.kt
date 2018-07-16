package com.wd.mms.model.storage

import android.content.Context
import android.content.SharedPreferences
import com.wd.mms.model.auth.AuthHolder
import javax.inject.Inject

class AuthPrefs @Inject constructor(private val context: Context) : AuthHolder {

    companion object {
        private const val AUTH_DATA = "auth_data"
        private const val TOKEN = "pref_token"
    }

    override var token: String?
        get() = getSharedPreferences(AUTH_DATA).getString(TOKEN, null)
        set(value) {
            getSharedPreferences(AUTH_DATA).edit().putString(TOKEN, "Token $value").apply()
        }

    private fun getSharedPreferences(name: String): SharedPreferences {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

}