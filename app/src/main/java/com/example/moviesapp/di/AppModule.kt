package com.example.moviesapp.di

import com.example.moviesapp.data.api.TheMovieDBClient
import com.example.moviesapp.ui.popularmovie.MoviePagedListRepository
import com.example.moviesapp.ui.singlemoviedetails.MovieDetailsRepository
import toothpick.config.Module

class AppModule : Module() {

    init {
        bind(TheMovieDBClient::class.java).singleton()
        bind(MoviePagedListRepository::class.java).singleton()
        bind(MovieDetailsRepository::class.java).singleton()
    }
}