package com.example.moviesapp.ui.popularmovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.moviesapp.data.api.MovieApiService
import com.example.moviesapp.data.model.Movie
import com.example.moviesapp.data.repository.MovieDataSource
import com.example.moviesapp.data.repository.MovieDataSourceFactory
import com.example.moviesapp.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MoviePagedListRepository @Inject constructor(
    private val movieApiService: MovieApiService
) {

    companion object {

        private const val POST_PER_PAGE = 20
    }

    lateinit var moviePagedList: LiveData<PagedList<Movie>>
    lateinit var movieDataSourceFactory: MovieDataSourceFactory

    fun fetchLiveMoviePageList(compositeDisposable: CompositeDisposable): LiveData<PagedList<Movie>> {
        movieDataSourceFactory = MovieDataSourceFactory(movieApiService, compositeDisposable)

        val config = PagedList.Config
            .Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
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