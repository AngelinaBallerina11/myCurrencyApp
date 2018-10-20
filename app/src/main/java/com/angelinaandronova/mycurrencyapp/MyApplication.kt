package com.angelinaandronova.mycurrencyapp

import android.app.Application
import com.angelinaandronova.mycurrencyapp.di.AppComponent
import com.angelinaandronova.mycurrencyapp.di.DaggerAppComponent
import com.angelinaandronova.mycurrencyapp.di.modules.AppModule
import com.angelinaandronova.mycurrencyapp.di.modules.NetworkModule


open class MyApplication : Application() {

    companion object {
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        @Suppress("DEPRECATION")
        component = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .networkModule(NetworkModule())
            .build()
    }
}