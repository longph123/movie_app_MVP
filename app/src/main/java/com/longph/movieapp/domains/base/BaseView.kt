package com.longph.movieapp.domains.base

import com.hannesdorfmann.mosby3.mvp.MvpView

interface BaseView : MvpView {
    fun showLoading()
    fun hideLoading()
    fun getApiError(throwable: Throwable)
}