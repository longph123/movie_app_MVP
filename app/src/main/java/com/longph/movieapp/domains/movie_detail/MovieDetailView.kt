package com.longph.movieapp.domains.movie_detail

import com.longph.movieapp.domains.base.BaseView
import com.longph.movieapp.models.MovieDetail

interface MovieDetailView : BaseView {
    fun getMovieDetailSuccess(movieDetail: MovieDetail)
}