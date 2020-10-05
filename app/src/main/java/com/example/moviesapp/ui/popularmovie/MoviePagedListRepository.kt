package com.example.moviesapp.ui.popularmovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.moviesapp.data.api.TheMovieDBClient
import com.example.moviesapp.data.repository.MovieDataSource
import com.example.moviesapp.data.repository.MovieDataSourceFactory
import com.example.moviesapp.data.repository.NetworkState
import com.example.moviesapp.data.vo.MovieResponse.Movie
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MoviePagedListRepository @Inject constructor(theMovieDBClient: TheMovieDBClient) {

    private val apiService = theMovieDBClient.getClient()
    lateinit var moviePagedList: LiveData<PagedList<Movie>>
    lateinit var movieDataSourceFactory: MovieDataSourceFactory

    fun fetchLiveMoviePageList(compositeDisposable: CompositeDisposable): LiveData<PagedList<Movie>> {
        movieDataSourceFactory = MovieDataSourceFactory(apiService, compositeDisposable)

        val config = PagedList.Config
            .Builder()
            .setEnablePlaceholders(false)
            .setPageSize(TheMovieDBClient.POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(movieDataSourceFactory, config).build()
        return moviePagedList
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap(
            movieDataSourceFactory.movieListDataSource,
            MovieDataSource::networkState
        )
    }
}