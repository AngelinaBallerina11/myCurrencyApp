package com.angelinaandronova.mycurrencyapp.network.rates.model

import com.angelinaandronova.mycurrencyapp.ui.main.CurrencyData

/* RatesService: /latest GET endpoint */
data class LatestRates(
    val base: String,
    val date: String,
    val rates: Map<String, Double>
) {
    fun transformMaptoList(): ArrayList<CurrencyData> {
        val output = arrayListOf<CurrencyData>()
        rates.forEach {
            output.add(CurrencyData(it.key, it.value))
        }
        return output
    }
}
