package com.longph.movieapp.networks

import android.accounts.NetworkErrorException
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.google.gson.Gson
import com.longph.movieapp.filters.NetworkFilter
import com.longph.movieapp.models.responses.RestResponse
import io.reactivex.Observable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class DefaultNetworkProvider : NetworkProvider {

    var context: Context
    var timeout: Long = 60

    constructor(context: Context) {
        this.context = context
    }

    override fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    override fun <T : Any> provideApi(baseUrl: String, apiClass: Class<T>): T {

        var builder = OkHttpClient.Builder()
        builder.connectTimeout(timeout, TimeUnit.SECONDS)
        builder.readTimeout(timeout, TimeUnit.SECONDS)
        builder.writeTimeout(timeout, TimeUnit.SECONDS)

        builder.addInterceptor(Interceptor { chain ->
            chain.proceed(chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json").build())
        })

        var httpLoggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        builder.addInterceptor(httpLoggingInterceptor)

        var okHttpClient: OkHttpClient = builder.build()

        var restAdapter = Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()

        return restAdapter.create(apiClass)
    }

    override fun <T : RestResponse<V>, V> parseResponse(call: Observable<T>): Observable<V> {
        var res: Observable<T> = call.observeOn(Schedulers.computation())
        res = res.onErrorResumeNext(Function { throwable -> NetworkFilter<T>(this).execute(throwable) })
        var result: Observable<V> = res.flatMap({ t -> Observable.just(t.results) })
        return result.onExceptionResumeNext(Observable.empty())
    }

    override fun <T> parseObjectResponse(call: Observable<T>): Observable<T> {
        var res: Observable<T> = call.observeOn(Schedulers.computation())
        res = res.onErrorResumeNext(Function { throwable -> NetworkFilter<T>(this).execute(throwable) })
        var result: Observable<T> = res.flatMap({ t -> Observable.just(t) })
        return result.onExceptionResumeNext(Observable.empty())
    }
}