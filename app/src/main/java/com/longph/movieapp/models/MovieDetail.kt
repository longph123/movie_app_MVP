package com.longph.movieapp.models

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class MovieDetail(
        @Id(assignable = true)
        var id: Long,
        var original_title: String,
        var overview: String,
        var poster_path: String,
        var vote_average: Double
)