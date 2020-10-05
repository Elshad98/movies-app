package com.example.moviesapp.ui.singlemoviedetails

import androidx.lifecycle.LiveData
import com.example.moviesapp.data.api.TheMovieDBClient
import com.example.moviesapp.data.repository.MovieDetailsNetworkDataSource
import com.example.moviesapp.data.repository.NetworkState
import com.example.moviesapp.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MovieDetailsRepository @Inject constructor(theMovieDBClient: TheMovieDBClient) {

    private val apiService = theMovieDBClient.getClient()
    lateinit var movieDetailsNetworkDataSource: MovieDetailsNetworkDataSource

    fun fetchSingleMovieDetails(
        compositeDisposable: CompositeDisposable,
        movieId: Int
    ): LiveData<MovieDetails> {
        movieDetailsNetworkDataSource =
            MovieDetailsNetworkDataSource(apiService, compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)

        return movieDetailsNetworkDataSource.downloadedMovieDetailsResponse
    }

    fun getMovieNetworkState(): LiveData<NetworkState> {
        return movieDetailsNetworkDataSource.networkState
    }
}