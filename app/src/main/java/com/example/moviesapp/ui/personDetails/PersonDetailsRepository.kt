package com.example.moviesapp.ui.personDetails

import androidx.lifecycle.LiveData
import com.example.moviesapp.data.api.TheMovieDBClient
import com.example.moviesapp.data.repository.NetworkState
import com.example.moviesapp.data.repository.PersonDetailsNetworkDataSource
import com.example.moviesapp.data.model.PersonDetails
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class PersonDetailsRepository @Inject constructor(theMovieDBClient: TheMovieDBClient) {

    private val apiService = theMovieDBClient.getClient()
    lateinit var personDetailsNetworkDataSource: PersonDetailsNetworkDataSource

    fun fetchPersonDetails(
        compositeDisposable: CompositeDisposable,
        personId: Int
    ): LiveData<PersonDetails> {
        personDetailsNetworkDataSource =
            PersonDetailsNetworkDataSource(apiService, compositeDisposable)
        personDetailsNetworkDataSource.fetchPersonDetails(personId)

        return personDetailsNetworkDataSource.downloadedPersonMovieDetails
    }

    fun getPersonNetworkState(): LiveData<NetworkState> {
        return personDetailsNetworkDataSource.networkState
    }
}