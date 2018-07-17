package com.wd.mms.toothpick.module

import com.google.gson.Gson
import com.wd.mms.BuildConfig
import com.wd.mms.model.data.Api
import com.wd.mms.model.interactor.LoginInteractor
import com.wd.mms.model.interactor.MessageInteractor
import com.wd.mms.model.repository.login.LoginRepository
import com.wd.mms.model.repository.message.MessageRepository
import com.wd.mms.toothpick.provider.ApiProvider
import com.wd.mms.toothpick.provider.GsonProvider
import com.wd.mms.toothpick.provider.OkHttpClientProvider
import com.wd.mms.toothpick.qualifier.ServerPath
import okhttp3.OkHttpClient
import toothpick.config.Module

class ServerModule : Module() {

    init {
        bind(String::class.java).withName(ServerPath::class.java).toInstance(BuildConfig.ORIGIN_ENDPOINT)
        bind(Gson::class.java).toProvider(GsonProvider::class.java)
        bind(OkHttpClient::class.java).toProvider(OkHttpClientProvider::class.java).providesSingletonInScope()
        bind(Api::class.java).toProvider(ApiProvider::class.java).providesSingletonInScope()

        bind(MessageInteractor::class.java)
        bind(MessageRepository::class.java)

        bind(LoginInteractor::class.java)
        bind(LoginRepository::class.java)
    }

}