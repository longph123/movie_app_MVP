package com.longph.movieapp.filters

interface Filter<Input, Output> {
    fun execute(source: Input): Output
}