package com.angelinaandronova.mycurrencyapp.network.currencyMetaData.service

import com.angelinaandronova.mycurrencyapp.network.currencyMetaData.model.CountryCurrencyInfo
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path


interface CurrencyDataService {

    companion object {
        const val COUNTRY_ENDPOINT = "v2/name"
    }

    @GET("$COUNTRY_ENDPOINT/{country}")
    fun getCountryInfo(@Path("country") country: String): Observable<List<CountryCurrencyInfo>>

}
