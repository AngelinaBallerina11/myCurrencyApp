package com.angelinaandronova.mycurrencyapp.di.modules

import android.app.Application
import android.content.Context
import com.angelinaandronova.mycurrencyapp.network.currencyMetaData.service.CurrencyDataService
import com.angelinaandronova.mycurrencyapp.network.rates.service.RatesService
import com.angelinaandronova.mycurrencyapp.ui.main.MainRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    internal fun provideApplication(): Application = application

    @Provides
    internal fun provideContext(): Context = application.applicationContext

    @Provides
    fun provideMainRepository(ratesService: RatesService,
                              currencyDataService: CurrencyDataService
    ): MainRepository =
        MainRepository(ratesService, currencyDataService)

}