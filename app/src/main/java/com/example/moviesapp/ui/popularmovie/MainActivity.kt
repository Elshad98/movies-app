package com.example.moviesapp.ui.popularmovie

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviesapp.R
import com.example.moviesapp.data.api.TheMovieDBClient
import com.example.moviesapp.data.api.TheMovieDBInterface
import com.example.moviesapp.data.repository.NetworkState
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel
    private val movieAdapter = PopularMoviePagedListAdapter(this)
    lateinit var movieRepository: MoviePagedListRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gridLayoutManager = GridLayoutManager(this, 3)
        val apiService: TheMovieDBInterface = TheMovieDBClient.getClient()

        movieRepository = MoviePagedListRepository(apiService)
        viewModel = getViewModel()

        gridLayoutManager.spanSizeLookup = spanSizeLookup

        rv_movie_list.apply {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            adapter = movieAdapter
        }

        viewModel.moviePagedList.observe(this, Observer { movieAdapter.submitList(it) })
        viewModel.networkState.observe(this, stateObserve)
    }

    private val spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {

        override fun getSpanSize(position: Int): Int {
            val viewType = movieAdapter.getItemViewType(position)
            return if (viewType == PopularMoviePagedListAdapter.MOVIE_VIEW_TYPE) 1 else 3
        }
    }

    private val stateObserve = Observer<NetworkState> { networkState ->
        progress_bar_popular.visibility =
            if (viewModel.listIsEmpty() && networkState == NetworkState.LOADING) View.VISIBLE else View.GONE
        txt_error_popular.visibility =
            if (viewModel.listIsEmpty() && networkState == NetworkState.ERROR) View.VISIBLE else View.GONE

        if (!viewModel.listIsEmpty()) {
            movieAdapter.setNetworkState(networkState)
        }
    }

    private fun getViewModel(): MainActivityViewModel {
        return ViewModelProvider(
            this,
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return MainActivityViewModel(movieRepository) as T
                }
            }
        )[MainActivityViewModel::class.java]
    }
}
