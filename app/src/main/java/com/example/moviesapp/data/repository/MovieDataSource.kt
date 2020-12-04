package com.example.moviesapp.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.moviesapp.data.api.MovieApiService
import com.example.moviesapp.data.model.Movie
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDataSource(
    private val movieApiService: MovieApiService,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, Movie>() {

    companion object {

        private const val TAG = "MovieDataSource"
        private const val FIRST_PAGE = 1
    }

    private var page = FIRST_PAGE

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            movieApiService.getPopularMovie(page)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { movieResponse ->
                        callback.onResult(movieResponse.movieList, null, page + 1)
                        networkState.postValue(NetworkState.LOADED)
                    },
                    { throwable ->
                        networkState.postValue(NetworkState.ERROR)
                        throwable.message?.let { Log.e(TAG, it) }
                    }
                )
        )
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Movie>
    ) {
        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            movieApiService.getPopularMovie(params.key)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { movieResponse ->
                        if (movieResponse.totalPages >= params.key) {
                            callback.onResult(movieResponse.movieList, params.key + 1)
                            networkState.postValue(NetworkState.LOADED)
                        } else {
                            networkState.postValue(NetworkState.END_OF_LIST)
                        }
                    },
                    { throwable ->
                        networkState.postValue(NetworkState.ERROR)
                        throwable.message?.let { Log.e(TAG, it) }
                    }
                )
        )
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Movie>
    ) {
        // Noop
    }
}