package com.longph.movieapp.domains.movie_list

import com.longph.movieapp.domains.base.BasePresenter
import com.longph.movieapp.models.Movie
import com.longph.movieapp.models.enums.MovieListType
import com.longph.movieapp.services.MovieService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MovieListPresenter : BasePresenter<MovieListView> {

    var movieService: MovieService
    private var disposable: Disposable? = null

    @Inject
    constructor(movieService: MovieService) {
        this.movieService = movieService
    }

    fun getMovieList(movieListType: MovieListType, page: Int) {
        if (isViewAttached) {
            view.showLoading()
        }
        if (disposable != null && disposable?.isDisposed == false) {
            disposable?.dispose()
        }

        var movieObservable: Observable<ArrayList<Movie>>

        if (movieListType == MovieListType.POPULAR) {
            movieObservable = movieService.getPopularMovieList(page)
        } else {
            movieObservable = movieService.getTopRatedMovieList(page)
        }

        disposable = movieObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnTerminate({
                    if (isViewAttached) {
                        view.hideLoading()
                    }
                })
                .subscribe({ movieList ->
                    view.getMovieListSuccess(movieList)
                }, { throwable ->
                    view.getApiError(throwable)
                })
    }

}