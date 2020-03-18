package com.longph.movieapp.filters

import io.reactivex.ObservableTransformer

interface InterceptFilter {
    abstract fun <T> execute(): ObservableTransformer<T, T>
}