package com.angelinaandronova.mycurrencyapp.network.model


data class LatestRates(
    val base: String,
    val date: String,
    val rates: Rates
)