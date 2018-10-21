package com.angelinaandronova.mycurrencyapp.ui.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.angelinaandronova.mycurrencyapp.network.currencyMetaData.model.CountryCurrencyInfo
import com.angelinaandronova.mycurrencyapp.network.rates.model.Currency
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(private val mainRepo: MainRepository) : ViewModel() {

    val currencyData = MutableLiveData<ArrayList<CurrencyData>>()

    init {
        fetchExchangeRates()
    }

    private fun fetchExchangeRates() {
        mainRepo.getExchangeRates()
            .subscribeOn(Schedulers.computation())
            .observeOn(Schedulers.computation())
            .map { it.rates.getListOfCurrencyData() }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .subscribeBy(
                onNext = { currencyData.postValue(it) }
            )
    }


    private fun log(msg: String) {
        Log.i("ANGELINA", msg)
    }
}

data class CurrencyData(val code: String,  var exchangeRate: Double? = null) {
    override fun toString(): String {
        return "CurrencyData(code='$code', exchangeRate=$exchangeRate)"
    }
}


