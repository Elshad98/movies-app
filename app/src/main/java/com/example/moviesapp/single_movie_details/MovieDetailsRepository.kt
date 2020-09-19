package com.example.moviesapp.single_movie_details

import androidx.lifecycle.LiveData
import com.example.moviesapp.data.api.TheMovieDBInterface
import com.example.moviesapp.data.repository.MovieDetailsNetworkDataSource
import com.example.moviesapp.data.repository.NetworkState
import com.example.moviesapp.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRepository(
    private val apiService: TheMovieDBInterface
) {

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