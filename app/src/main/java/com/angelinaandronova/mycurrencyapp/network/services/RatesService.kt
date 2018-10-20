package com.angelinaandronova.mycurrencyapp.network.services

import retrofit2.http.GET


interface RatesService {

    @GET()
    fun getRates()
}