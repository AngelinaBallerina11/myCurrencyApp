package com.angelinaandronova.mycurrencyapp.network.rates.service

import retrofit2.http.GET


interface RatesService {

    @GET()
    fun getRates()
}