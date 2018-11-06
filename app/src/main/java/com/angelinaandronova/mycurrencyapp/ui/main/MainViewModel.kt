package com.angelinaandronova.mycurrencyapp.ui.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainViewModel @Inject constructor(private val mainRepo: MainRepository) : ViewModel() {

    val currencyData = MutableLiveData<ArrayList<CurrencyData>>()
    val error = MutableLiveData<Throwable>()
    var timesequenceObservable: Disposable? = null

    fun startFetchingRates() {
        timesequenceObservable = Observable
            .interval(1, TimeUnit.SECONDS)
            .timeInterval()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .subscribeBy(
                onNext = { fetchExchangeRates() }
            )
    }

    private fun fetchExchangeRates() {
        mainRepo.getExchangeRates()
            .subscribeOn(Schedulers.computation())
            .observeOn(Schedulers.computation())
            .map { it.transformMaptoList() }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .subscribeBy(
                onNext = { currencyData.postValue(it) },
                onError = {
                    error.postValue(it)
                    timesequenceObservable?.dispose()
                }
            )
    }

    var editMode = false
        set(value) {
            if (value) {
                field = true
                GlobalScope.launch {
                    delay(5000) /* edit mode can be set max to 5 seconds */
                    field = false
                }
            } else {
                field = false
            }

        }


    var items: java.util.ArrayList<CurrencyData> = arrayListOf()
}

data class CurrencyData(val code: String, var exchangeRate: Double? = null) {
    override fun toString(): String {
        return "CurrencyData(code='$code', exchangeRate=$exchangeRate)"
    }
}


