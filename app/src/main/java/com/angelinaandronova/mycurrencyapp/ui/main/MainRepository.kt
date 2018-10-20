package com.angelinaandronova.mycurrencyapp.ui.main

import com.angelinaandronova.mycurrencyapp.network.model.images.CurrencyDataResponse
import com.angelinaandronova.mycurrencyapp.network.services.CurrencyDataService
import com.angelinaandronova.mycurrencyapp.network.services.RatesService
import io.reactivex.Observable


class MainRepository(private val exchangeRatesService: RatesService,
                     private val currencyDataService: CurrencyDataService) {

    fun getImageByCurrencyCode(code: String): Observable<List<CurrencyDataResponse>> = currencyDataService.getCodeInfo(code)


}