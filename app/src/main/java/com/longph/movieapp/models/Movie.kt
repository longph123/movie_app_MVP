package com.longph.movieapp.models

import com.longph.movieapp.models.enums.MovieListType
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Movie(
        @Id(assignable = true)
        var id: Long,
        var vote_count: Int,
        var video: Boolean,
        var vote_average: Float,
        var title: String,
        var popularity: Double,
        var poster_path: String,
        var original_language: String,
        var original_title: String,
        var backdrop_path: String,
        var adult: Boolean,
        var overview: String,
        var release_date: String,
        var movieType: Int
)