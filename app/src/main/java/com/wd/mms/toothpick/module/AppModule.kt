package com.wd.mms.toothpick.module

import android.content.Context
import com.wd.mms.model.auth.AuthHolder
import com.wd.mms.model.storage.AuthPrefs
import com.wd.mms.model.system.ResourceManager
import toothpick.config.Module


class AppModule(context: Context) : Module() {

    init {
        bind(Context::class.java).toInstance(context)
        bind(ResourceManager::class.java)
        bind(AuthHolder::class.java).to(AuthPrefs::class.java).singletonInScope()
    }
}
