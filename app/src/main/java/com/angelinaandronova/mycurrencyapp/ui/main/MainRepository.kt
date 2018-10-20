package com.angelinaandronova.mycurrencyapp.ui.main

import com.angelinaandronova.mycurrencyapp.network.currencyMetaData.model.CurrencyDataResponse
import com.angelinaandronova.mycurrencyapp.network.currencyMetaData.service.CurrencyDataService
import com.angelinaandronova.mycurrencyapp.network.rates.service.RatesService
import io.reactivex.Observable


class MainRepository(private val exchangeRatesService: RatesService,
                     private val currencyDataService: CurrencyDataService
) {

    fun getImageByCurrencyCode(code: String): Observable<List<CurrencyDataResponse>> = currencyDataService.getCodeInfo(code)


}