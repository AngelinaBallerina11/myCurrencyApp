package com.angelinaandronova.mycurrencyapp.network.model.rates


data class LatestRates(
    val base: String,
    val date: String,
    val rates: Rates
)