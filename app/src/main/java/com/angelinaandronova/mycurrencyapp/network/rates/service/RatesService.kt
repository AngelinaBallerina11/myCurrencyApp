package com.angelinaandronova.mycurrencyapp.network.rates.service

import com.angelinaandronova.mycurrencyapp.network.rates.model.Currency
import com.angelinaandronova.mycurrencyapp.network.rates.model.LatestRates
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface RatesService {

    companion object {
        const val LATEST_EXCHANGE_RATES_ENDPOINT = "latest"
    }

    @GET(LATEST_EXCHANGE_RATES_ENDPOINT)
    fun getRates(@Query("base") baseCurrencyCode: String = Currency.EUR.name): Observable<LatestRates>
}