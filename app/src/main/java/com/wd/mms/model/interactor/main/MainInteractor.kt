package com.wd.mms.model.interactor.main

import com.wd.mms.model.auth.AuthHolder
import javax.inject.Inject

class MainInteractor @Inject constructor(private val authHolder: AuthHolder) {

    fun isTokenEmpty() = authHolder.token.isNullOrEmpty()

    fun clearAllUserData() {
        authHolder.clearData()
    }
}