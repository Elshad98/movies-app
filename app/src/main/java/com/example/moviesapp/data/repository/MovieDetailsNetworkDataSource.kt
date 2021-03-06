package com.example.moviesapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviesapp.data.api.MovieApiService
import com.example.moviesapp.data.model.MovieDetails
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDetailsNetworkDataSource(
    private val movieApiService: MovieApiService,
    private val compositeDisposable: CompositeDisposable
) {

    companion object {

        private const val TAG = "MovieDetailsNetworkDS"
    }

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _downloadedMovieDetailsResponse = MutableLiveData<MovieDetails>()
    val downloadedMovieDetailsResponse: LiveData<MovieDetails>
        get() = _downloadedMovieDetailsResponse

    fun fetchMovieDetails(movieId: Int) {
        _networkState.postValue(NetworkState.LOADING)

        try {
            compositeDisposable.add(
                movieApiService.getMovieDetails(movieId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        { movieDetails ->
                            _downloadedMovieDetailsResponse.postValue(movieDetails)
                            _networkState.postValue(NetworkState.LOADED)
                        },
                        { throwable ->
                            _networkState.postValue(NetworkState.ERROR)
                            throwable.message?.let { Log.e(TAG, it) }
                        }
                    )
            )
        } catch (exc: Exception) {
            exc.message?.let { Log.e(TAG, it) }
        }
    }
}