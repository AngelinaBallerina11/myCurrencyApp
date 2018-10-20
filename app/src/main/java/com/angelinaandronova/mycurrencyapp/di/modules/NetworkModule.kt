package com.angelinaandronova.mycurrencyapp.di.modules

import com.angelinaandronova.mycurrencyapp.network.services.CurrencyDataService
import com.angelinaandronova.mycurrencyapp.network.services.RatesService
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
open class NetworkModule {

    companion object {
        const val BASE_URL_REVOLUT = "https://revolut.duckdns.org"
        const val BASE_URL_IMAGES = "https://restcountries.eu/rest/"
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Provides
    @Singleton
    fun provideRatesService(gson: Gson, okHttpClient: OkHttpClient): RatesService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .baseUrl(BASE_URL_REVOLUT)
            .build()
            .create(RatesService::class.java)
    }

    @Provides
    @Singleton
    fun provideImagesService(gson: Gson, okHttpClient: OkHttpClient): CurrencyDataService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .baseUrl(BASE_URL_IMAGES)
            .build()
            .create(CurrencyDataService::class.java)
    }
}