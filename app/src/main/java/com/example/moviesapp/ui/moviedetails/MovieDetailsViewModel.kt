package com.example.moviesapp.ui.moviedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviesapp.data.repository.NetworkState
import com.example.moviesapp.data.model.MovieDetails
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(
    private val movieRepository: MovieDetailsRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    fun getMovieDetails(movieId: Int): LiveData<MovieDetails> {
        return movieRepository.fetchSingleMovieDetails(compositeDisposable, movieId)
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return movieRepository.getMovieNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}