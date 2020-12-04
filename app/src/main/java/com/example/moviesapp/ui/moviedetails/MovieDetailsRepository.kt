package com.example.moviesapp.ui.moviedetails

import androidx.lifecycle.LiveData
import com.example.moviesapp.data.api.MovieApiService
import com.example.moviesapp.data.model.MovieDetails
import com.example.moviesapp.data.repository.MovieDetailsNetworkDataSource
import com.example.moviesapp.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MovieDetailsRepository @Inject constructor(
    private val movieApiService: MovieApiService
) {

    lateinit var movieDetailsNetworkDataSource: MovieDetailsNetworkDataSource

    fun fetchSingleMovieDetails(
        compositeDisposable: CompositeDisposable,
        movieId: Int
    ): LiveData<MovieDetails> {
        movieDetailsNetworkDataSource =
            MovieDetailsNetworkDataSource(movieApiService, compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)

        return movieDetailsNetworkDataSource.downloadedMovieDetailsResponse
    }

    fun getMovieNetworkState(): LiveData<NetworkState> {
        return movieDetailsNetworkDataSource.networkState
    }
}