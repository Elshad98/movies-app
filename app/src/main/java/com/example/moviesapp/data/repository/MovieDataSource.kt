package com.example.moviesapp.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.moviesapp.data.api.TheMovieDBClient
import com.example.moviesapp.data.api.TheMovieDBInterface
import com.example.moviesapp.data.vo.MovieResponse
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDataSource(
    private val apiService: TheMovieDBInterface,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, MovieResponse.Movie>() {

    companion object {

        private const val TAG = "MovieDataSource"
    }

    private var page = TheMovieDBClient.FIRST_PAGE

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, MovieResponse.Movie>
    ) {
        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getPopularMovie(page)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { movieResponse ->
                        callback.onResult(movieResponse.movieList, null, page + 1)
                        networkState.postValue(NetworkState.LOADED)
                    },
                    { throwable ->
                        networkState.postValue(NetworkState.ERROR)
                        Log.e(TAG, throwable.message)
                    }
                )
        )
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, MovieResponse.Movie>
    ) {
        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getPopularMovie(params.key)
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
                        Log.e(TAG, throwable.message)
                    }
                )
        )
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, MovieResponse.Movie>
    ) {
    }
}