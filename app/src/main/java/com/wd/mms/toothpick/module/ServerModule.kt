package com.wd.mms.toothpick.module

import com.wd.mms.model.data.Api
import com.wd.mms.toothpick.provider.ApiProvider
import com.wd.mms.toothpick.provider.OkHttpClientProvider
import com.wd.mms.toothpick.qualifier.ServerPath
import okhttp3.OkHttpClient
import toothpick.config.Module

class ServerModule(serverUrl: String) : Module() {

    init {
        bind(String::class.java).withName(ServerPath::class.java).toInstance(serverUrl)
        bind(OkHttpClient::class.java).toProvider(OkHttpClientProvider::class.java).providesSingletonInScope()
        bind(Api::class.java).toProvider(ApiProvider::class.java).providesSingletonInScope()

    }

}