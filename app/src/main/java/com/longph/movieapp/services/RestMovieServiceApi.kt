package com.longph.movieapp.services

import com.longph.movieapp.models.Movie
import com.longph.movieapp.models.MovieDetail
import com.longph.movieapp.models.responses.RestResponse
import io.reactivex.Observable
import org.androidannotations.annotations.ViewById
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RestMovieServiceApi {

    @GET("movie/popular")
    fun getPopularMovieLists(@Query("api_key") apiKey: String,
                             @Query("language") language: String,
                             @Query("page") page: Int): Observable<RestResponse<ArrayList<Movie>>>

    @GET("movie/top_rated")
    fun getTopRatedMovieLists(@Query("api_key") apiKey: String,
                              @Query("language") language: String,
                              @Query("page") page: Int): Observable<RestResponse<ArrayList<Movie>>>

    @GET("movie/{movie_id}")
    fun getMovieDetail(@Path("movie_id") movieId: Long,
                       @Query("api_key") apiKey: String,
                       @Query("language") language: String): Observable<MovieDetail>
}