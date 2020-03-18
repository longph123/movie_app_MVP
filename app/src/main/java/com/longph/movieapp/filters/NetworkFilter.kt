package com.longph.movieapp.filters

import com.longph.movieapp.models.ApiThrowable
import com.longph.movieapp.networks.NetworkProvider
import com.longph.movieapp.utils.ErrorCodes
import io.reactivex.Observable

class NetworkFilter<T> : Filter<Throwable, Observable<T>> {

    var networkProvider: NetworkProvider

    constructor(networkProvider: NetworkProvider) {
        this.networkProvider = networkProvider
    }

    override fun execute(source: Throwable): Observable<T> {
        if (!networkProvider.isNetworkAvailable()) {
            return Observable.error(ApiThrowable(ErrorCodes.NETWORK_NOT_AVAILABLE_ERROR,
                    "Unable to contact the server"))
        }

        return Observable.error(source)
    }
}