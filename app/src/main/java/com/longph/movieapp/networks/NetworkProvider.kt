package com.longph.movieapp.networks

import com.longph.movieapp.models.responses.RestResponse
import io.reactivex.Observable

interface NetworkProvider {

    fun <T : Any> provideApi(baseUrl: String, apiClass: Class<T>): T
    fun <T : RestResponse<V>, V> parseResponse(call: Observable<T>): Observable<V>
    fun <T> parseObjectResponse(call: Observable<T>): Observable<T>
    fun isNetworkAvailable(): Boolean
}