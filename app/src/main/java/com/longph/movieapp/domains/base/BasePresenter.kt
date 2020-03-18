package com.longph.movieapp.domains.base

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import javax.inject.Inject

abstract class BasePresenter<V : BaseView> : MvpBasePresenter<V> {

    constructor() : super()
}