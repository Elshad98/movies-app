package com.example.moviesapp.ui.persondetails

import androidx.lifecycle.LiveData
import com.example.moviesapp.data.api.MovieApiService
import com.example.moviesapp.data.model.PersonDetails
import com.example.moviesapp.data.repository.NetworkState
import com.example.moviesapp.data.repository.PersonDetailsNetworkDataSource
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class PersonDetailsRepository @Inject constructor(
    private val movieApiService: MovieApiService
) {

    lateinit var personDetailsNetworkDataSource: PersonDetailsNetworkDataSource

    fun fetchPersonDetails(
        compositeDisposable: CompositeDisposable,
        personId: Int
    ): LiveData<PersonDetails> {
        personDetailsNetworkDataSource =
            PersonDetailsNetworkDataSource(movieApiService, compositeDisposable)
        personDetailsNetworkDataSource.fetchPersonDetails(personId)

        return personDetailsNetworkDataSource.downloadedPersonMovieDetails
    }

    fun getPersonNetworkState(): LiveData<NetworkState> {
        return personDetailsNetworkDataSource.networkState
    }
}