package com.example.moviesapp.ui.personDetails

import com.example.moviesapp.data.api.TheMovieDBClient
import javax.inject.Inject

class PersonDetailsRepository @Inject constructor(theMovieDBClient: TheMovieDBClient) {

    private val apiService = theMovieDBClient.getClient()
}