package com.wd.mms.model.data.auth

import com.wd.mms.model.auth.AuthHolder
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


class AuthInterceptor @Inject constructor(val authHolder: AuthHolder) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        authHolder.token?.let {
            request = request.newBuilder()
                    .addHeader("Cache-Control", "no-cache")
                    .addHeader("Authorization", it)
                    .build()
        }
        return chain.proceed(request)
    }
}

