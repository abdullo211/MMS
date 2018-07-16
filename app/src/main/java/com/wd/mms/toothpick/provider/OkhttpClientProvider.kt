package com.wd.mms.toothpick.provider

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Provider

class OkHttpClientProvider  @Inject constructor(

) : Provider<OkHttpClient> {

    override fun get() = with(OkHttpClient.Builder()) {
        connectTimeout(30, TimeUnit.SECONDS)
        readTimeout(30, TimeUnit.SECONDS)
        addNetworkInterceptor(HttpLoggingInterceptor())
        build()
    }
}
