package com.angelinaandronova.myimagedownloadapplication.di

import com.angelinaandronova.mycurrencyapp.ui.main.MainFragment
import com.angelinaandronova.myimagedownloadapplication.di.modules.AppModule
import com.angelinaandronova.myimagedownloadapplication.di.modules.NetworkModule
import com.angelinaandronova.myimagedownloadapplication.di.modules.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(mainFragment: MainFragment)
}