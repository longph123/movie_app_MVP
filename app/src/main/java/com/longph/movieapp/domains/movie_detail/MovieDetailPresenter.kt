package com.longph.movieapp.domains.movie_detail

import com.longph.movieapp.domains.base.BasePresenter
import com.longph.movieapp.models.Movie
import com.longph.movieapp.models.enums.MovieListType
import com.longph.movieapp.services.MovieService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MovieDetailPresenter : BasePresenter<MovieDetailView> {

    var movieService: MovieService
    private var disposable: Disposable? = null

    @Inject
    constructor(movieService: MovieService) {
        this.movieService = movieService
    }

    fun getMovieDetail(movieId: Long) {
        if (isViewAttached) {
            view.showLoading()
        }
        if (disposable != null && disposable?.isDisposed == false) {
            disposable?.dispose()
        }

        disposable = movieService.getMovieDetail(movieId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnTerminate({
                    if (isViewAttached) {
                        view.hideLoading()
                    }
                })
                .subscribe({ movieDetail ->
                    view.getMovieDetailSuccess(movieDetail)
                }, { throwable ->
                    view.getApiError(throwable)
                })
    }
}