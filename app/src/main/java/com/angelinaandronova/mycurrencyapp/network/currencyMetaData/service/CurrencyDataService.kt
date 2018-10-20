package com.angelinaandronova.mycurrencyapp.network.currencyMetaData.service

import com.angelinaandronova.mycurrencyapp.network.currencyMetaData.model.CurrencyDataResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path


interface CurrencyDataService {

    companion object {
        const val CURRENCY_ENDPOINT = "v2/currency"
    }

    @GET("$CURRENCY_ENDPOINT/{currencyCode}")
    fun getCodeInfo(@Path("currencyCode") currencyCode: String): Observable<List<CurrencyDataResponse>>

}
