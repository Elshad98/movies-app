package com.example.moviesapp.ui.popularmovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.moviesapp.data.api.POST_PER_PAGE
import com.example.moviesapp.data.api.TheMovieDBInterface
import com.example.moviesapp.data.repository.MovieDataSource
import com.example.moviesapp.data.repository.MovieDataSourceFactory
import com.example.moviesapp.data.repository.NetworkState
import com.example.moviesapp.data.vo.MovieResponse.Movie
import io.reactivex.disposables.CompositeDisposable

class MoviePagedListRepository(
    private val apiService: TheMovieDBInterface
) {

    lateinit var moviePagedList: LiveData<PagedList<Movie>>
    lateinit var movieDataSourceFactory: MovieDataSourceFactory

    fun fetchLiveMoviePageList(compositeDisposable: CompositeDisposable): LiveData<PagedList<Movie>> {
        movieDataSourceFactory = MovieDataSourceFactory(apiService, compositeDisposable)

        var config = PagedList.Config
            .Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(movieDataSourceFactory, config).build()
        return moviePagedList
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<MovieDataSource, NetworkState>(
            movieDataSourceFactory.movieListDataSource,
            MovieDataSource::networkState
        )
    }
}