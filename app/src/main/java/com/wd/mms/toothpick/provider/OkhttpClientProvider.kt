package com.wd.mms.toothpick.provider

import com.wd.mms.model.auth.AuthHolder
import com.wd.mms.model.data.auth.AuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Provider

class OkHttpClientProvider @Inject constructor(val authHolder: AuthHolder
) : Provider<OkHttpClient> {

    override fun get() = with(OkHttpClient.Builder()) {
        connectTimeout(30, TimeUnit.SECONDS)
        readTimeout(30, TimeUnit.SECONDS)
        addInterceptor(AuthInterceptor(authHolder))
        addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        build()
    }
}
