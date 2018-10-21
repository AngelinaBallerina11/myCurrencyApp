package com.angelinaandronova.mycurrencyapp.network.rates.model

import com.angelinaandronova.mycurrencyapp.ui.main.CurrencyData

/* RatesService: /latest GET endpoint */
data class LatestRates(
    val base: String,
    val date: String,
    val rates: Rates
)

data class Rates(
    val AUD: Double,
    val BGN: Double,
    val BRL: Double,
    val CAD: Double,
    val CHF: Double,
    val CNY: Double,
    val CZK: Double,
    val DKK: Double,
    val GBP: Double,
    val HKD: Double,
    val HRK: Double,
    val HUF: Double,
    val IDR: Double,
    val ILS: Double,
    val INR: Double,
    val ISK: Double,
    val JPY: Double,
    val KRW: Double,
    val MXN: Double,
    val MYR: Double,
    val NOK: Double,
    val NZD: Double,
    val PHP: Double,
    val PLN: Double,
    val RON: Double,
    val RUB: Double,
    val SEK: Double,
    val SGD: Double,
    val THB: Double,
    val TRY: Double,
    val USD: Double,
    val ZAR: Double
) {
    fun getListOfCurrencyData(): ArrayList<CurrencyData> {
        val resultingList = arrayListOf<CurrencyData>()
        mapOf(
            Currency.AUD.name to this.AUD,
            Currency.BGN.name to this.BGN,
            Currency.BRL.name to this.BRL,
            Currency.CAD.name to this.CAD,
            Currency.CHF.name to this.CHF,
            Currency.CNY.name to this.CNY,
            Currency.CZK.name to this.CZK,
            Currency.DKK.name to this.DKK,
            Currency.GBP.name to this.GBP,
            Currency.HKD.name to this.HKD,
            Currency.HRK.name to this.HRK,
            Currency.HUF.name to this.HUF,
            Currency.IDR.name to this.IDR,
            Currency.ILS.name to this.ILS,
            Currency.INR.name to this.INR,
            Currency.ISK.name to this.ISK,
            Currency.JPY.name to this.JPY,
            Currency.KRW.name to this.KRW,
            Currency.MXN.name to this.MXN,
            Currency.MYR.name to this.MYR,
            Currency.NOK.name to this.NOK,
            Currency.NZD.name to this.NZD,
            Currency.PHP.name to this.PHP,
            Currency.PLN.name to this.PLN,
            Currency.RON.name to this.RON,
            Currency.RUB.name to this.RUB,
            Currency.SEK.name to this.SEK,
            Currency.SGD.name to this.SGD,
            Currency.THB.name to this.THB,
            Currency.TRY.name to this.TRY,
            Currency.USD.name to this.USD,
            Currency.ZAR.name to this.ZAR
        ).forEach { resultingList.add(CurrencyData(it.key, it.value)) }
        return resultingList
    }
}