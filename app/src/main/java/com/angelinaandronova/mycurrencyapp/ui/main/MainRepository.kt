package com.angelinaandronova.mycurrencyapp.ui.main

import com.angelinaandronova.mycurrencyapp.network.rates.model.LatestRates
import com.angelinaandronova.mycurrencyapp.network.rates.service.RatesService
import io.reactivex.Observable


class MainRepository(
    private val exchangeRatesService: RatesService
) {

    fun getExchangeRates(): Observable<LatestRates> {
        return exchangeRatesService.getRates()
    }


}