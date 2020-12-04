package com.example.moviesapp.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.moviesapp.data.api.MovieApiService
import com.example.moviesapp.data.model.Movie
import io.reactivex.disposables.CompositeDisposable

class MovieDataSourceFactory(
    private val movieApiService: MovieApiService,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, Movie>() {

    val movieListDataSource = MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val movieDataSource = MovieDataSource(movieApiService, compositeDisposable)

        movieListDataSource.postValue(movieDataSource)
        return movieDataSource
    }
}