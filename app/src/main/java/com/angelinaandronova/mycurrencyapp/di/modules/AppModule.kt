package com.angelinaandronova.mycurrencyapp.di.modules

import android.app.Application
import android.content.Context
import com.angelinaandronova.mycurrencyapp.ui.main.MainRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    internal fun provideApplication(): Application = application

    @Provides
    internal fun provideContext(): Context = application.applicationContext

    @Provides
    fun provideMainRepository(retrofit: Retrofit): MainRepository = MainRepository(retrofit)

}