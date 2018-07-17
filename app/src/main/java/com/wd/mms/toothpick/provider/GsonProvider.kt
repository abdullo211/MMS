package com.wd.mms.toothpick.provider

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.wd.mms.model.data.server.DateDeserializer
import org.threeten.bp.LocalDateTime
import javax.inject.Inject
import javax.inject.Provider

class GsonProvider @Inject constructor() : Provider<Gson> {

    override fun get(): Gson =
            GsonBuilder()
                    .registerTypeAdapter(LocalDateTime::class.java, DateDeserializer())
                    .create()
}
