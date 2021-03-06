package com.example.moviesapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviesapp.data.api.MovieApiService
import com.example.moviesapp.data.model.PersonDetails
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PersonDetailsNetworkDataSource(
    private val movieApiService: MovieApiService,
    private val compositeDisposable: CompositeDisposable
) {

    companion object {

        private const val TAG = "PersonDetailsNetworkDS"
    }

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _downloadedPersonDetailsResponse = MutableLiveData<PersonDetails>()
    val downloadedPersonMovieDetails: LiveData<PersonDetails>
        get() = _downloadedPersonDetailsResponse

    fun fetchPersonDetails(personId: Int) {
        _networkState.postValue(NetworkState.LOADING)

        try {
            compositeDisposable.add(
                movieApiService.getPersonDetails(personId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        { personDetails ->
                            _downloadedPersonDetailsResponse.postValue(personDetails)
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