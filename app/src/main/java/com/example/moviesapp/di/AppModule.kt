package com.example.moviesapp.di

import com.example.moviesapp.data.api.MovieApiService
import com.example.moviesapp.ui.moviedetails.MovieDetailsRepository
import com.example.moviesapp.ui.persondetails.PersonDetailsRepository
import com.example.moviesapp.ui.popularmovie.MoviePagedListRepository
import toothpick.config.Module

class AppModule : Module() {

    init {
        bind(MovieApiService::class.java).toProvider(MovieApiServiceProvider::class.java)
            .providesSingleton()
        bind(MoviePagedListRepository::class.java).singleton()
        bind(MovieDetailsRepository::class.java).singleton()
        bind(PersonDetailsRepository::class.java).singleton()
    }
}