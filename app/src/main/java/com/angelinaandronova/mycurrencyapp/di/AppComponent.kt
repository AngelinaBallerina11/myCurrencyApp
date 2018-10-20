package com.angelinaandronova.mycurrencyapp.di

import com.angelinaandronova.mycurrencyapp.ui.main.MainFragment
import com.angelinaandronova.mycurrencyapp.di.modules.AppModule
import com.angelinaandronova.mycurrencyapp.di.modules.NetworkModule
import com.angelinaandronova.mycurrencyapp.di.modules.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(mainFragment: MainFragment)
}