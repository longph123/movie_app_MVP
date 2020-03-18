package com.longph.movieapp.services

import com.longph.movieapp.models.Movie
import com.longph.movieapp.models.MovieDetail
import io.reactivex.Observable

interface MovieService {

    fun getPopularMovieList(page: Int): Observable<ArrayList<Movie>>
    fun getTopRatedMovieList(page: Int): Observable<ArrayList<Movie>>
    fun getMovieDetail(movieId: Long): Observable<MovieDetail>
}