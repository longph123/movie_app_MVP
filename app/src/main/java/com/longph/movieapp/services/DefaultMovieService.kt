package com.longph.movieapp.services

import com.longph.movieapp.filters.ErrorFilter
import com.longph.movieapp.models.Movie
import com.longph.movieapp.models.MovieDetail
import com.longph.movieapp.networks.NetworkProvider
import com.longph.movieapp.utils.ApiInfos
import io.reactivex.Observable
import io.reactivex.functions.Function
import javax.inject.Inject

class DefaultMovieService : MovieService {

    var networkProvider: NetworkProvider
    var restMovieServiceApi: RestMovieServiceApi
    var errorFilter: ErrorFilter

    @Inject
    constructor(networkProvider: NetworkProvider, restMovieServiceApi: RestMovieServiceApi, errorFilter: ErrorFilter) {
        this.networkProvider = networkProvider
        this.restMovieServiceApi = restMovieServiceApi
        this.errorFilter = errorFilter
    }

    override fun getPopularMovieList(page: Int): Observable<ArrayList<Movie>> {
        return networkProvider.parseResponse(
                restMovieServiceApi.getPopularMovieLists(ApiInfos.API_KEY, "en-US", page))
                .compose(errorFilter.execute())
                .flatMap(Function { movieList -> Observable.just(movieList) })
    }

    override fun getTopRatedMovieList(page: Int): Observable<ArrayList<Movie>> {
        return networkProvider.parseResponse(
                restMovieServiceApi.getTopRatedMovieLists(ApiInfos.API_KEY, "en-US", page))
                .compose(errorFilter.execute())
                .flatMap(Function { movieList -> Observable.just(movieList) })
    }

    override fun getMovieDetail(movieId: Long): Observable<MovieDetail> {
        return networkProvider.parseObjectResponse(
                restMovieServiceApi.getMovieDetail(movieId, ApiInfos.API_KEY, "en-US"))
                .compose(errorFilter.execute())
                .flatMap(Function { movieDetail -> Observable.just(movieDetail) })
    }
}