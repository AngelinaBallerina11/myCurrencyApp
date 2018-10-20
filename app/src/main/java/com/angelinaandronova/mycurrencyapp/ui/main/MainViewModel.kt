package com.angelinaandronova.mycurrencyapp.ui.main

import android.arch.lifecycle.ViewModel
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(private val mainRepo: MainRepository) : ViewModel() {


    init {
        mainRepo.getImageByCurrencyCode("AUD")
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { log(it[0].flag) },
                onError = { log(it.localizedMessage) },
                onComplete = { log("done!!") }
            )
    }

    private fun log(msg: String) {
        Log.i("ANGELINA", msg)
    }
}


