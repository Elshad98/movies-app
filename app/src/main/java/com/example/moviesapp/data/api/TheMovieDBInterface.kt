package com.example.moviesapp.data.api

import com.example.moviesapp.data.model.MovieDetails
import com.example.moviesapp.data.model.MovieResponse
import com.example.moviesapp.data.model.PersonDetails
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDBInterface {

    @GET("movie/popular")
    fun getPopularMovie(@Query("page") page: Int): Single<MovieResponse>

    @GET("movie/{movie_id}?append_to_response=credits,release_dates,similar,videos")
    fun getMovieDetails(@Path("movie_id") id: Int): Single<MovieDetails>

    @GET("person/{person_id}?append_to_response=movie_credits")
    fun getPersonDetails(@Path("person_id") id: Int): Single<PersonDetails>
}