package com.wd.mms

import android.app.Application
import android.support.multidex.MultiDexApplication
import com.wd.mms.toothpick.DI
import com.wd.mms.toothpick.module.AppModule
import com.wd.mms.toothpick.module.ServerModule
import io.realm.Realm
import toothpick.Toothpick

class App : MultiDexApplication() {


    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        initAppScope()
    }

    private fun initAppScope() {
        val appScope = Toothpick.openScope(DI.APP_SCOPE)
        appScope.installModules(AppModule(this))

        val serverScope = Toothpick.openScopes(DI.APP_SCOPE, DI.SERVER_SCOPE)
        serverScope.installModules(ServerModule())
    }


}