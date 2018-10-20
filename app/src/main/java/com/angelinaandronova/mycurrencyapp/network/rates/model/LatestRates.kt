package com.angelinaandronova.mycurrencyapp.network.rates.model

/* RatesService: /latest GET endpoint */
data class LatestRates(
    val base: String,
    val date: String,
    val rates: Rate
)

data class Rate(
    val currencyCode: String,
    val rate: Double
)