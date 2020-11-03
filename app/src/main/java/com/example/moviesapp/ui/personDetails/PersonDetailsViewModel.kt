package com.example.moviesapp.ui.personDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviesapp.data.repository.NetworkState
import com.example.moviesapp.data.vo.PersonDetails
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class PersonDetailsViewModel @Inject constructor(
    private val personDetailsRepository: PersonDetailsRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    fun getPersonDetails(personId: Int): LiveData<PersonDetails> {
        return personDetailsRepository.fetchPersonDetails(compositeDisposable, personId)
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return personDetailsRepository.getPersonNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}