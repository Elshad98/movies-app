package com.example.moviesapp.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.moviesapp.data.api.TheMovieDBInterface
import com.example.moviesapp.data.vo.MovieResponse
import io.reactivex.disposables.CompositeDisposable

class MovieDataSourceFactory(
    private val apiService: TheMovieDBInterface,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, MovieResponse.Movie>() {

    val movieListDataSource = MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, MovieResponse.Movie> {
        val movieDataSource = MovieDataSource(apiService, compositeDisposable)

        movieListDataSource.postValue(movieDataSource)
        return movieDataSource
    }
}