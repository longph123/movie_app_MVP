package com.longph.movieapp.filters

import android.content.Intent
import android.widget.Toast
import com.longph.movieapp.networks.NetworkProvider
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function

class ErrorFilter(private val networkProvider: NetworkProvider) : InterceptFilter {

    override fun <T> execute(): ObservableTransformer<T, T> {

        return ObservableTransformer { observable ->
            observable.observeOn(AndroidSchedulers.mainThread())
                    .onErrorResumeNext(Function { throwable -> Observable.error(throwable) })
        }
    }
}