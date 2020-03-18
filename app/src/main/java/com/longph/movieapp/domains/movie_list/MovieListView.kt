package com.longph.movieapp.domains.movie_list

import com.longph.movieapp.domains.base.BaseView
import com.longph.movieapp.models.Movie

interface MovieListView : BaseView {
    fun getMovieListSuccess(movieList: ArrayList<Movie>)
}