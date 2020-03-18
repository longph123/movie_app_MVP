package com.longph.movieapp.infrastructures

import com.longph.movieapp.domains.movie_detail.MovieDetailActivity
import com.longph.movieapp.domains.movie_list.MovieListActivity
import com.longph.movieapp.infrastructures.scopes.ActivityScope
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface MainComponent {
    fun inject(movieListActivity: MovieListActivity)
    fun inject(movieListActivity: MovieDetailActivity)
}